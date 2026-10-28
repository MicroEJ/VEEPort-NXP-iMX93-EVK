@echo off

REM BAT
REM Copyright 2026 MicroEJ Corp. All rights reserved.
REM Use of this source code is governed by a BSD-style license that can be found with this software.
REM
REM Build: 7E4D1F7C

REM 'deploy_keystore.bat' deploys a PKCS#12 keystore to the target device.

REM Save application current directory and jump one level above scripts
SET CURRENT_DIRECTORY=%CD%
CD "%~dp0\..\"

SET BSP_ROOT_DIRECTORY=%~dp0..\..\

CALL "scripts\set_project_env.bat"
IF %ERRORLEVEL% NEQ 0 (
	exit /B %ERRORLEVEL%
)

SET VARS=SSH_HOSTNAME=%SSH_HOSTNAME% SSH_USER=%SSH_USER%

WSL -d %WSL_DISTRIBUTION_NAME% --cd %CURRENT_DIRECTORY% bash -c "dos2unix $(wslpath '%BSP_ROOT_DIRECTORY%')vee/scripts/*.sh"
IF %ERRORLEVEL% NEQ 0 (
	exit /B %ERRORLEVEL%
)

WSL -d %WSL_DISTRIBUTION_NAME% --cd %CURRENT_DIRECTORY% bash -c "%VARS% bash $(wslpath '%BSP_ROOT_DIRECTORY%')/vee/scripts/deploy_keystore.sh"
IF %ERRORLEVEL% NEQ 0 (
	exit /B %ERRORLEVEL%
)

CD "%CURRENT_DIRECTORY%"
