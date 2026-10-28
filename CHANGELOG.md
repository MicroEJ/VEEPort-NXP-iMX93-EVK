# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [3.2.0] - 2026-10-28

### Added

- Add a PKCS#11 External KeyStore backend backed by the OP-TEE PKCS#11 TA (ARM TrustZone + NXP EdgeLock Secure Enclave), via the OpenSSL pkcs11-provider.
- Add NOTICE.txt listing third-party component licenses (MicroUI, Embunit) and publish it with the VEE Port.
- Wire `gradle clean` to the BSP clean script so the abstraction layer C build outputs are removed as well.
- Add AES key generation with `javax.crypto.KeyGenerator` (128, 192 and 256 bits).
- Add the error codes that the Crypto Foundation Library 2.3.0 declares.

### Changed

- Update to MicroEJ SDK Gradle plugins 1.8.0.
- Update the Linux Abstraction Layer to 3.5.0.
- Update the Yocto SDK and image to 1.1.0 (i.MX Yocto scarthgap 6.6.52). Migration: install the new SDK and flash the new image.
- Update to Architecture 8.6.0.
- Update to NET Pack 15.0.1 and the NET Test Suite to 4.2.1.
- Update to UI Pack 14.5.2.
- Update to FS Pack 6.0.5.
- Update the front panel framework to 1.3.0 and the front panel widget library to 5.4.2.
- Configure the display vsync, framebuffer and touchscreen device paths, and 32bpp to 16bpp conversion at build time instead of via runtime environment variables (vsync via the `BUILD_UI_VSYNC` CMake option, default `ON`). The application now runs with no runtime environment.
- Migrate the External KeyStore from the Security (`LLSEC`) API to the Crypto (`LLCRYPTO`) API to match the abstraction layer: use the `crypto` Foundation Library and Test Suite, rename the `security` validation suite to `crypto`, and rename the `LLSEC_*` keystore build/runtime variables to `LLCRYPTO_*`.
- Update the SSL Foundation Library to 2.3.1 and the SSL Test Suites to 6.0.0 (OpenJDK 3.0.0).
- Update the Device Foundation Library to 1.4.1.
- Update the Crypto Foundation Library and the Crypto Test Suite to 2.3.0.
- Update the Event Foundation Library to 3.0.7 and the Event Test Suite to 3.1.0 (major version change).

### Fixed

- Fix `ej.bon.Util.setCurrentTimeMillis()` ignoring a failure to set the clock: it now throws a `NativeException`.

## [3.1.0] - 2025-09-08

### Added

- Added Event pack

### Changed

- Update to MicroEJ SDK gradle plugin 1.3.1
- MicroEJ Architecture updated to version 8.4.0
- MicroEJ Core test suite updated to 3.5.0
- MicroEJ UI Architecture updated to version 14.4.2
- Update to OSAL 1.0.3 and Async-worker 0.6.0
- Implement event queue on top of OSAL_queue instead of mqueue
- Update to NET 12.2.0 & Security 4.2.0

### Fixed

- Fixed a timeout in osal posix
- Fixed run.sh script for application.out deployment

## [3.0.2] - 2025-03-28

### Fixed

- Fixed an issue with Ivy builds

## [3.0.1] - 2025-03-25

### Fixed

- Set default build to Mono-Sandbox capability

## [3.0.0] - 2025-03-20

### Changed

- Migrate to SDK6/Gradle plugin 1.0.0
- MicroEJ Architecture updated to version 8.3.0
- MicroEJ UI Architecture updated to version 14.3.2
- MicroEJ NET Test suite updated to 4.1.2
- UI pack updated to 14.3.2

## [2.0.0] - 2024-11-18

### Changed

- Switch to generic linux abstraction layer (submodule)
  - new build system using cmake
  - refactor the toolchain and bsp options
- MicroEJ Architecture updated to version 8.1.1
- MicroEJ UI Architecture updated to version 14.0.2
- UI test suite update to 1.7.0
- MicroEJ NET Pack updated to version 11.1.0

### Fixed

- Fixed an issue with touchscreen disconnections
- Fixed a FS issue with large file offsets
- Fixed an issue where the executable never ends in multiapp mode

## [1.0.2] - 2024-04-30

### Fixed

- Fixed an issue with test suite builds

## [1.0.1] - 2024-04-30

### Fixed

- Fixed a crash with async worker

## [1.0.0] - 2024-04-24

### Fixed

- Optimize pthread stack size

### Added

- VEE Port i.MX93
- CORE
- FS
- UI
- NET
- SSL
- SECURITY

---
_Markdown_  
_Copyright 2024-2026 MicroEJ Corp. All rights reserved._  
_Use of this source code is governed by a BSD-style license that can be found with this software._  
_Build: 7E4D1F7C_  
