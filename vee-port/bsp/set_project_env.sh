#!/bin/bash
#
# BASH
#
# Copyright 2024 MicroEJ Corp. All rights reserved.
# Use of this source code is governed by a BSD-style license that can be found with this software.

# Toolchain settings:
# Yocto SDK installation path (e.g. /usr/local/oecore-x86_64)

YOCTO_SDK_INSTALL=/usr/local/oecore-x86_64
YOCTO_SDK_ENV_SCRIPT=environment-setup-armv7at2hf-neon-vfpv4-oemllib32-linux-gnueabi

if [ ! -d "$YOCTO_SDK_INSTALL" ]; then
	echo ""
	echo "ERROR: YOCTO_SDK_INSTALL="$YOCTO_SDK_INSTALL" is not a valid path."
	echo "Please verify set_project_env.sh"
	echo ""
	exit -1
fi


. "$YOCTO_SDK_INSTALL/$YOCTO_SDK_ENV_SCRIPT"

