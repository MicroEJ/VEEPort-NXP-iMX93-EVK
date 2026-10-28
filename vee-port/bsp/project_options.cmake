# Copyright 2024-2026 MicroEJ Corp. All rights reserved.
# Use of this source code is governed by a BSD-style license that can be found with this software.
#
# Build: 7E4D1F7C

#
# CFLAGS
#
# generate 32bit code, see https://gcc.gnu.org/onlinedocs/gcc/x86-Options.html
set(CFLAGS ${CFLAGS} -mbe32)

#
# Abstraction layer external source
#
# set(ABSTRACTION_LAYER_EXTERNAL ../../../linux-abstraction-layer-imx93)
# When specifying an out-of-tree source a binary directory must be explicitly specified
# here we will install the output files in a subdirectory of CMAKE_CURRENT_BINARY_DIR
# add_subdirectory(${ABSTRACTION_LAYER_EXTERNAL} linux-abstraction-layer-imx93)

#
# Abstraction layer options
#
# This section allows you to override the default CMake options that are defined in Linux Abstraction layer.

# For example to disable the following option defined in linux-abstraction-layer/vee/options.cmake
# option(BUILD_FS           "Build File System Abstraction Layer"            ON)
# You can just override its default value here, for example:
# set(BUILD_FS OFF)

# Optional features
# set(BUILD_UI_TOUCHSCREEN OFF)
# Debug features
set(BUILD_UI_FRAMERATE OFF)

# iMX93 specific options
# use libdrm by default
set(BUILD_UI_FBDRM ON)
set(BUILD_UI_FBDEV OFF)
# no serial port yet
set(BUILD_SERIAL OFF)
# no audio support
set(BUILD_AUDIO OFF)

# Debug features
set(ADVANCED_TRACE OFF)
set(USE_VEEPORT_LOGGER ON)

# Options generated from the Gradle build. -Pexperimental.rio.enabled=true turns
# BUILD_RIO on. See README.md, "Experimental Features".
include(${CMAKE_CURRENT_LIST_DIR}/gradle_options.cmake OPTIONAL)

# Test features
set(BUILD_VALIDATION OFF)
set(BUILD_VALIDATION_KERNEL OFF)
set(BUILD_VALIDATION_VEE_MOCK OFF)

#
# Crypto: PKCS#11 external keystore runtime configuration
#
# Baked at build time so the application runs with no runtime environment
# (no OPENSSL_CONF / OPENSSL_MODULES / LLCRYPTO_PKCS11_* needed). Paths are the
# iMX93 OP-TEE target paths.
set(LLCRYPTO_PKCS11_MODULE "/usr/lib/libckteec.so.0")
set(LLCRYPTO_OSSL_MODULES_DIR "/usr/lib/ossl-modules")

#
# Crypto: PKCS#12 external keystore file path
#
# Baked at build time so the application runs with no runtime environment.
# Mirrors the deploy target (KEYSTORE_TARGET in set_project_env.sh) so the binary
# reads exactly where deploy_keystore.sh scp's the .p12 on the iMX93 target. Only
# consumed by the PKCS12 backend (ignored when LLCRYPTO_KEYSTORE_TYPE=PKCS11). When
# KEYSTORE_TARGET is not exported (e.g. a bare cmake configure), the C #ifndef
# fallback applies.
if(NOT "$ENV{KEYSTORE_TARGET}" STREQUAL "")
    set(LLCRYPTO_KEYSTORE_PATH "$ENV{KEYSTORE_TARGET}")
endif()
