# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

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
_Copyright 2024-2025 MicroEJ Corp. All rights reserved._  
_Use of this source code is governed by a BSD-style license that can be found with this software._  
