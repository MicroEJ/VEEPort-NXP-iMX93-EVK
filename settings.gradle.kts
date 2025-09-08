/*
 * Kotlin
 *
 * Copyright 2024-2025 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */

rootProject.name = "nxp-imx93-evk"
include("vee-port", "vee-port:front-panel", "vee-port:mock", "vee-port:image-generator", "app")

project(":vee-port:front-panel").projectDir = file("vee-port/extensions/front-panel")
//project(":vee-port:mock").projectDir = file("vee-port/mock")
//project(":vee-port:image-generator").projectDir = file("vee-port/extensions/image-generator")

include("vee-port:validation:core")
include("vee-port:validation:event-queue")
include("vee-port:validation:fs")
include("vee-port:validation:net")
include("vee-port:validation:security")
include("vee-port:validation:ssl")
include("vee-port:validation:ui")

dependencyResolutionManagement {
    versionCatalogs {
        create("libsOverride") {
            from(files("./linux-abstraction-layer/gradle/libs.versions.toml"))
        }
    }
}
