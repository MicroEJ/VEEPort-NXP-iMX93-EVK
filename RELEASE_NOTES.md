# VEE Port Release Notes for NXP i.MX93EVK

## Description

This is the release notes of the VEE Port for i.MX93EVK.

## Versions

### VEE Port

3.1.0

### Dependencies

This VEE Port contains the following dependencies:

| Dependency Name              | Version |
|------------------------------|---------|
| Architecture (oliveARMv8A_1) | 8.4.0   |
| Pack UI                      | 14.3.2  |
| Pack FS                      | 6.0.4   |
| Pack NET                     | 11.1.0  |
| Pack ECOM-NETWORK            | 1.0.0   |
| Pack DEVICE                  | 1.2.0   |
| Pack EVENT                   | 2.1.0   |
   
Please refer to the Abstraction Layer [README Abstraction Layer Linux](https://github.com/MicroEJ/AbstractionLayer-Linux/blob/master/README.md) for more details.

### Board Support Package

- BSP provider and name: NXP
- BSP version: v6.1.55

Please refer to the NXP git repository available [here](https://github.com/nxp-imx/meta-imx)

### Third Party Software

This VEE port relies on opensource software that can be found [here](https://github.com/nxp-imx/meta-imx).

Here is a list of the most important ones:

|              |         |         |
|--------------|---------|---------|
| RTOS         | Linux   | 6.1.55  |
| Cryptography | OpenSSL | 3.1.3   |
| Display      | libdrm  | 2.4.114 |
| Touchscreen  | tslib   | 1.22    |

## Features

### Graphical User Interface

This VEE Port features a graphical user interface.
It is implemented over linux DRM library, or the frame buffer device (configurable in the BSP).

### Network

This VEE Port features a network interface with ethernet as an
underlying hardware media.

### SSL

VEE Port features a network secure interface. Available
secured protocol is TLS 1.2. Supported keys and
certificates formats are PKCS#5 and PKCS#12, PEM or DER encoded.

### File System

This VEE Port features a file system interface.

## MISRA Compliance

This VEE Port has a list of components that are MISRA-compliant (MISRA C:2012) with some noted exception.
Below is the list of folders that have been verified:

- `linux-abstraction-layer\projects\microej\core`

It has been verified with Cppcheck v2.13. Here is the list of deviations from MISRA standard:

| Deviation | Category | Justification                                                                                  |
|-----------|----------|------------------------------------------------------------------------------------------------|
| Rule 5.5  | Required | Function names are declared as a macro                                                         |
| Rule 8.4  | Required | Declaration is not visible without an application                                              |
| Rule 17.7 | Required | Unused non-void returned type, the use of<br/>returned values of debug traces is not necessary |


## Known Issues/Limitations

Known issues:

- N/A

Limitations:

- N/A

---
_Markdown_
_Copyright 2024-2025 MicroEJ Corp. All rights reserved._
_Use of this source code is governed by a BSD-style license that can be found with this software._
