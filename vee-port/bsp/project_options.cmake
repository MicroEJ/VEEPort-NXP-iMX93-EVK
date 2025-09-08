# Copyright 2024-2025 MicroEJ Corp. All rights reserved.
# Use of this source code is governed by a BSD-style license that can be found with this software.

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

# Debug features
set(ADVANCED_TRACE OFF)

# Test features
set(BUILD_VALIDATION OFF)
