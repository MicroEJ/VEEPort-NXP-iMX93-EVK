#!/bin/sh

# Copyright 2026 MicroEJ Corp. All rights reserved.
# Use of this source code is governed by a BSD-style license that can be found with this software.
#
# Build: 7E4D1F7C

# Provision an OP-TEE PKCS#11 token with test keys and certificates for
# External KeyStore validation tests. Extracts key material from test-keystore.p12
# and imports it into the OP-TEE PKCS#11 TA via pkcs11-tool.
#
# Run this script directly on the target board (iMX93):
#   scp setup-optee-test-token.sh root@<board-ip>:/tmp/
#   scp test-keystore.p12 root@<board-ip>:/etc/keystore.p12
#   ssh root@<board-ip> /tmp/setup-optee-test-token.sh
#
# Prerequisites on the target:
#   - optee-client (libckteec.so)
#   - opensc (pkcs11-tool)
#   - openssl
#
# After provisioning, the token is ready for the security validation test suite.

set -eu

# --- Configuration ---
TOKEN_LABEL="MicroEJ-Test"
USER_PIN="changeit"
SO_PIN="12345678"
SLOT=0
P12_FILE="${1:-/etc/keystore.p12}"
P12_PASS="changeit"

# Find 64-bit libckteec.so (pkcs11-tool is 64-bit on aarch64 boards)
PKCS11_MODULE=""
for candidate in \
    /usr/lib64/libckteec.so.0 \
    /usr/lib64/libckteec.so \
    /usr/lib/libckteec.so.0 \
    /usr/lib/libckteec.so; do
    if [ -f "${candidate}" ]; then
        PKCS11_MODULE="${candidate}"
        break
    fi
done

if [ -z "${PKCS11_MODULE}" ]; then
    echo "ERROR: Cannot find libckteec.so. Install optee-client."
    exit 1
fi

echo "Using PKCS#11 module: ${PKCS11_MODULE}"

if [ ! -f "${P12_FILE}" ]; then
    echo "ERROR: Keystore file not found: ${P12_FILE}"
    echo "Usage: $0 [path-to-test-keystore.p12]"
    exit 1
fi

P11="pkcs11-tool --module ${PKCS11_MODULE}"
P11_TOKEN="${P11} --token-label ${TOKEN_LABEL} --pin ${USER_PIN}"
WORK="/tmp/pkcs11-setup-$$"
mkdir -p "${WORK}"
trap "rm -rf ${WORK}" EXIT

# --- Initialize token ---
echo "Initializing OP-TEE PKCS#11 token '${TOKEN_LABEL}' on slot ${SLOT}..."
${P11} --init-token --slot ${SLOT} --label "${TOKEN_LABEL}" --so-pin "${SO_PIN}"
${P11} --init-pin --slot ${SLOT} --login --so-pin "${SO_PIN}" --pin "${USER_PIN}"

# --- Extract key material from test-keystore.p12 ---
echo "Extracting key material from ${P12_FILE}..."

# Extract all keys and certs as PEM with friendlyName metadata.
# The friendlyName attribute is the reliable identifier for each object.
openssl pkcs12 -in "${P12_FILE}" -passin "pass:${P12_PASS}" -nocerts -nodes \
    -out "${WORK}/all-keys.pem" 2>/dev/null
openssl pkcs12 -in "${P12_FILE}" -passin "pass:${P12_PASS}" -nokeys \
    -out "${WORK}/all-certs.pem" 2>/dev/null

# Extract test-entity private key by friendlyName in PKCS#8 DER format.
# OP-TEE PKCS#11 TA requires PKCS#8 format — SEC 1 EC format is silently ignored.
awk '/friendlyName: test-entity/{found=1; next} found && /BEGIN PRIVATE KEY/{p=1} p{print} p && /END PRIVATE KEY/{exit}' \
    "${WORK}/all-keys.pem" | openssl pkcs8 -topk8 -nocrypt -outform DER -out "${WORK}/entity-key.der" 2>/dev/null
echo "  Private key extracted ($(wc -c < "${WORK}/entity-key.der") bytes, PKCS#8 DER)"

# Extract test-entity certificate by friendlyName
awk '/friendlyName: test-entity/{found=1; next} found && /BEGIN CERT/{p=1} p{print} p && /END CERT/{exit}' \
    "${WORK}/all-certs.pem" | openssl x509 -outform DER -out "${WORK}/entity-cert.der" 2>/dev/null
echo "  Entity cert extracted ($(wc -c < "${WORK}/entity-cert.der") bytes)"

# Extract test-ca certificate by friendlyName ($ anchor to avoid matching test-entity-ca)
awk '/friendlyName: test-ca$/{found=1; next} found && /BEGIN CERT/{p=1} p{print} p && /END CERT/{exit}' \
    "${WORK}/all-certs.pem" | openssl x509 -outform DER -out "${WORK}/ca-cert.der" 2>/dev/null
echo "  CA cert extracted ($(wc -c < "${WORK}/ca-cert.der") bytes)"

# Verify extraction
for f in entity-key.der entity-cert.der ca-cert.der; do
    if [ ! -s "${WORK}/${f}" ]; then
        echo "ERROR: Failed to extract ${f} from ${P12_FILE}"
        exit 1
    fi
done

# --- Import objects into token ---
echo "Importing objects into OP-TEE PKCS#11 token..."

# test-entity: EC private key (with sign + derive usage for ECDH)
echo "  Importing test-entity private key..."
${P11_TOKEN} --write-object "${WORK}/entity-key.der" --type privkey \
    --label "test-entity" --id 01 --usage-sign --usage-derive

# test-entity: certificate
echo "  Importing test-entity certificate..."
${P11_TOKEN} --write-object "${WORK}/entity-cert.der" --type cert \
    --label "test-entity" --id 01

# test-entity-ca: CA certificate (for chain building)
echo "  Importing test-entity-ca certificate..."
${P11_TOKEN} --write-object "${WORK}/ca-cert.der" --type cert \
    --label "test-entity-ca" --id 02

# test-trusted-cert: entity certificate as trusted entry
echo "  Importing test-trusted-cert certificate..."
${P11_TOKEN} --write-object "${WORK}/entity-cert.der" --type cert \
    --label "test-trusted-cert" --id 03

# test-secret: AES-128 key (generated on token)
# pkcs11-tool probes CKA_VERIFY_RECOVER (asymmetric-only) when displaying the new
# AES key; the token's CKR_ATTRIBUTE_TYPE_INVALID answer is spec-correct noise
# (https://github.com/OpenSC/OpenSC/issues/3333). Filter only that warning, keep
# any other stderr output visible ('|| true' because grep -v exits 1 when it
# filters every line, which would trip 'set -e').
echo "  Generating test-secret AES-128 key on token..."
${P11_TOKEN} --keygen --key-type AES:16 --label "test-secret" --id 04 --extractable \
    2> >(grep -v 'VERIFY_RECOVER.*CKR_ATTRIBUTE_TYPE_INVALID' >&2 || true)

# --- Verify ---
echo ""
echo "=== OP-TEE PKCS#11 Token Setup Complete ==="
echo ""
echo "Token contents:"
${P11_TOKEN} -O 2>/dev/null
echo ""
echo "Token is ready for the MicroEJ security validation test suite."
echo "Aliases: test-entity, test-trusted-cert, test-entity-ca, test-secret"
