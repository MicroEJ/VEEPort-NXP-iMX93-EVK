# VEE Port Release Notes for NXP i.MX93EVK

## Description

This is the release notes of the VEE Port for i.MX93EVK.

## Versions

### VEE Port

3.2.0

### Dependencies

This VEE Port contains the following dependencies:

| Dependency Name              | Version |
|------------------------------|---------|
| Architecture (oliveARMv8A_1) | 8.6.0   |
| Pack UI                      | 14.5.2  |
| Pack FS                      | 6.0.5   |
| Pack NET                     | 15.0.1  |
| Pack ECOM-NETWORK            | 1.0.0   |
| CRYPTO Foundation Library    | 2.3.0   |
| DEVICE Foundation Library    | 1.4.1   |
| EVENT Foundation Library     | 3.0.7   |
| Linux Abstraction Layer      | 3.5.0   |

Please refer to the Abstraction Layer [README Abstraction Layer Linux](https://github.com/MicroEJ/AbstractionLayer-Linux/blob/master/README.md) for more details.

### Board Support Package

- BSP provider and name: NXP i.MX Yocto Project
- BSP version: imx-6.6.52-2.2.2 (scarthgap)
- MicroEJ Yocto image and SDK version: 1.1.0

Please refer to the NXP git repository available [here](https://github.com/nxp-imx/meta-imx)

### Third Party Software

This VEE port relies on opensource software that can be found [here](https://github.com/nxp-imx/meta-imx).

Here is a list of the most important ones:

|                 |                          |         |
|-----------------|--------------------------|---------|
| RTOS            | Linux                    | 6.6.52  |
| Cryptography    | OpenSSL                  | 3       |
| Secure storage  | OP-TEE, pkcs11-provider  |         |
| Display         | libdrm                   | 2.4.116 |
| Touchscreen     | tslib                    |         |

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

### Cryptography

This VEE Port implements the CRYPTO Foundation Library over OpenSSL.
It provides an External KeyStore with two backends, selected at build time:

- PKCS#11, over the OP-TEE PKCS#11 Trusted Application
- PKCS#12, over a keystore file on the target

### File System

This VEE Port features a file system interface.

## MISRA Compliance

The Linux Abstraction Layer documents the MISRA C:2012 compliance of its components and the list of deviations.
Please refer to the MISRA Compliance section of the [README Abstraction Layer Linux](https://github.com/MicroEJ/AbstractionLayer-Linux/blob/master/README.md).

## Known Issues/Limitations

Known issues:

- N/A

Limitations:

- N/A

---
_Markdown_
_Copyright 2024-2026 MicroEJ Corp. All rights reserved._
_Use of this source code is governed by a BSD-style license that can be found with this software._
