#!/bin/bash
#
# BASH
#
# Copyright 2024-2026 MicroEJ Corp. All rights reserved.
# Use of this source code is governed by a BSD-style license that can be found with this software.
#
# Build: 7E4D1F7C

# Toolchain settings:
# Yocto SDK installation path (e.g. /usr/local/oecore-x86_64)

YOCTO_SDK_INSTALL=/opt/multilib-fsl-imx-xwayland/6.6-scarthgap-1.1.0
YOCTO_SDK_ENV_SCRIPT=environment-setup-armv7at2hf-neon-pokymllib32-linux-gnueabi

if [ ! -d "$YOCTO_SDK_INSTALL" ]; then
	echo ""
	echo "ERROR: YOCTO_SDK_INSTALL="$YOCTO_SDK_INSTALL" is not a valid path."
	echo "Please verify set_project_env.sh"
	echo ""
	exit -1
fi


. "$YOCTO_SDK_INSTALL/$YOCTO_SDK_ENV_SCRIPT"

# External keystore path on the iMX93 target. The PKCS12 backend reads this
# path directly (it is baked into the application via LLCRYPTO_KEYSTORE_PATH
# in project_options.cmake, so no runtime env is needed); in PKCS11 mode it is
# the staging path imported into the OP-TEE token. Defaults to /etc; override by
# exporting KEYSTORE_TARGET before the build.
#
# LIMITATION: this is a fixed, target-global path, so only one application
# instance per target is supported. Concurrent instances would share and clobber
# the same keystore file.
KEYSTORE_TARGET="${KEYSTORE_TARGET:-/etc/keystore.p12}"

# External KeyStore backend selection (single source of truth for build and runtime).
# PKCS11 = PKCS#11 token via OpenSSL pkcs11-provider (OP-TEE TA on iMX93).
# PKCS12 = traditional .p12 file backend.
LLCRYPTO_KEYSTORE_TYPE=PKCS11
# The PKCS#11 module path and the OpenSSL provider modules directory are baked
# into the application at build time (see project_options.cmake), so they are
# no longer set or exported here. The OP-TEE PKCS#11 token and the
# pkcs11-provider are configured without any runtime environment on the target.
export LLCRYPTO_KEYSTORE_TYPE KEYSTORE_TARGET
