..
    Copyright 2024 MicroEJ Corp. All rights reserved.
    Use of this source code is governed by a BSD-style license that can be found with this software.

===========
 Changelog
===========

----------------------
 [2.0.0] - 2024-11-18
----------------------

Changed
=======

- Switch to generic linux abstraction layer (submodule)
  - new build system using cmake
  - refactor the toolchain and bsp options
- MicroEJ Architecture updated to version 8.1.1
- MicroEJ UI Architecture updated to version 14.0.2
- UI test suite update to 1.7.0
- MicroEJ NET Pack updated to version 11.1.0

Fixed
=====

- Fixed an issue with touchscreen disconnections
- Fixed a FS issue with large file offsets
- Fixed an issue where the executable never ends in multiapp mode

----------------------
 [1.0.2] - 2024-04-30
----------------------

Fixed
=====

- Fixed an issue with test suite builds

----------------------
 [1.0.1] - 2024-04-30
----------------------

Fixed
=====

- Fixed a crash with async worker

----------------------
 [1.0.0] - 2024-04-24
----------------------

Fixed
=====

- Optimize pthread stack size

Added
=====

- VEE Port i.MX93
- CORE
- FS
- UI
- NET
- SSL
- SECURITY
