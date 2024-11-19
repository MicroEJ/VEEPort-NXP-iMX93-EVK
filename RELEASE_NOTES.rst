..
    Copyright 2024 MicroEJ Corp. All rights reserved.
    Use of this source code is governed by a BSD-style license that can be found with this software..

.. |BOARD_NAME| replace:: i.MX93EVK
.. |VEEPORT_VER| replace:: 2.0.0
.. |VEEPORT| replace:: VEE Port
.. |MANUFACTURER| replace:: NXP

=======================================================
|VEEPORT| Release Notes for |MANUFACTURER| |BOARD_NAME|
=======================================================

Description
===========

This is the release notes of the |VEEPORT| for |BOARD_NAME|.

Versions
========

|VEEPORT|
---------

|VEEPORT_VER|

Dependencies
------------

This |VEEPORT| contains the following dependencies:

.. list-table::
   :header-rows: 1
   
   * - Dependency Name
     - Version
   * - Architecture (oliveARMv8A_1)
     - 8.1.1
   * - Pack UI
     - 14.0.2
   * - Pack FS
     - 6.0.4
   * - Pack NET
     - 11.1.0
   * - Pack ECOM-NETWORK
     - 1.0.0
   * - Pack DEVICE
     - 1.2.0

Please refer to the Abstraction Layer `README <https://github.com/MicroEJ/AbstractionLayer-Linux>`__ for more details.

Board Support Package
---------------------

- BSP provider and name: NXP
- BSP version: v6.1.55

Please refer to the NXP git repository
available `here
<https://github.com/nxp-imx/meta-imx>`__.

Third Party Software
--------------------

This VEE port relies on opensource software that can be found `here
<https://github.com/nxp-imx/meta-imx>`__. Here
is a list of the most important ones:

.. list-table::
   :widths: 3 3 3

   * - RTOS 
     - Linux
     - 6.1.55
   * - Cryptography 
     - OpenSSL
     - 3.1.3
   * - Display
     - libdrm
     - 2.4.114
   * - Touchscreen
     - tslib
     - 1.22

Features
========

Graphical User Interface
------------------------

This |VEEPORT| features a graphical user interface.
It is implemented over linux DRM library, or the frame buffer device (configurable in the BSP).

Network
-------

This |VEEPORT| features a network interface with ethernet as an
underlying hardware media.

SSL
-------

|VEEPORT| features a network secure interface. Available
secured protocol is TLS 1.2. Supported keys and
certificates formats are PKCS#5 and PKCS#12, PEM or DER encoded.

File System
-------

This |VEEPORT| features a file system interface.

MISRA Compliance
================

This VEE Port has a list of components that are MISRA-compliant (MISRA C:2012) with some noted exception.
Below is the list of folders that have been verified:

- ``Linux-abstractionlayer\projects\microej\core``

It has been verified with Cppcheck v2.13. Here is the list of deviations from MISRA standard:

+-----------+----------+---------------------------------------------+
| Deviation | Category | Justification                               |
+===========+==========+=============================================+
| Rule 5.5  | Required | Function names are declared as a macro      |
+-----------+----------+---------------------------------------------+
| Rule 8.4  | Required | Declaration is not visible without an       |
|           |          | application                                 |
+-----------+----------+---------------------------------------------+
| Rule 17.7 | Required | Unused non-void returned type, the use of   |
|           |          | returned values of debug traces is not      |
|           |          | necessary                                   |
+-----------+----------+---------------------------------------------+

Known Issues/Limitations
========================

Known issues:

- N/A

Limitations:

- N/A
