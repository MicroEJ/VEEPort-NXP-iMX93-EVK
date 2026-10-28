#!/bin/bash
#
# BASH
#
# Copyright 2026 MicroEJ Corp. All rights reserved.
# Use of this source code is governed by a BSD-style license that can be found with this software.
#
# Build: 7E4D1F7C

# Deploys the external keystore to the iMX93 target via SSH.
# When LLCRYPTO_KEYSTORE_TYPE=PKCS11, also provisions the OP-TEE PKCS#11 token
# and creates the OpenSSL pkcs11-provider config file on the target.
#
# No-op if no keystore file is found in the scripts directory.

set -euo pipefail

SCRIPTS_DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd) || exit

KEYSTORE_FILE="${SCRIPTS_DIR}/test-keystore.p12"

if [ ! -f "${KEYSTORE_FILE}" ]; then
    echo "No keystore file found at ${KEYSTORE_FILE}, skipping deployment."
    exit 0
fi

# Setup build environment variables (KEYSTORE_TARGET, SSH_*, ...)
. "${SCRIPTS_DIR}/set_env.sh"

# Default keystore target path if not set by the project
if [ -z "${KEYSTORE_TARGET:-""}" ]; then
    KEYSTORE_TARGET="/tmp/keystore.p12"
fi

# Note: the OpenSSL pkcs11-provider is configured programmatically by the LLCRYPTO
# port at runtime, so no openssl-pkcs11.cnf is written or deployed here.

# --------------------------------------------------------------------------
# Remote deployment (SSH)
# --------------------------------------------------------------------------
if [ -z "${SSH_HOSTNAME:-""}" ]; then
    echo "ERROR: Missing SSH_HOSTNAME environment variable"
    exit 1
fi

if [ -z "${SSH_USER:-""}" ]; then
    echo "ERROR: Missing SSH_USER environment variable"
    exit 1
fi

if [ -z "${SSH_PASSWORD:-""}" ]; then
    ssh_password_option=""
else
    ssh_password_option="sshpass -p ${SSH_PASSWORD}"
fi

echo "Deploying external keystore to ${SSH_USER}@${SSH_HOSTNAME}:${KEYSTORE_TARGET}..."
${ssh_password_option} scp -O -C -oStrictHostKeyChecking=no "${KEYSTORE_FILE}" "${SSH_USER}@${SSH_HOSTNAME}:${KEYSTORE_TARGET}"
echo "Keystore deployed successfully."

if [ "${LLCRYPTO_KEYSTORE_TYPE:-PKCS12}" = "PKCS11" ]; then
    # Provision OP-TEE PKCS#11 token
    SETUP_SCRIPT="${SCRIPTS_DIR}/setup-optee-test-token.sh"
    if [ -f "${SETUP_SCRIPT}" ]; then
        echo "Provisioning OP-TEE PKCS#11 token on ${SSH_HOSTNAME}..."
        ${ssh_password_option} scp -O -C -oStrictHostKeyChecking=no "${SETUP_SCRIPT}" "${SSH_USER}@${SSH_HOSTNAME}:/tmp/setup-optee-test-token.sh"
        ${ssh_password_option} ssh -oStrictHostKeyChecking=no "${SSH_USER}@${SSH_HOSTNAME}" "sh /tmp/setup-optee-test-token.sh ${KEYSTORE_TARGET}"
        echo "PKCS#11 token provisioned successfully."
    else
        echo "WARNING: PKCS11 mode enabled but ${SETUP_SCRIPT} not found. Token not provisioned."
    fi
fi
