/*
 * Kotlin
 *
 * Copyright 2024-2026 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 *
 * Build: 7E4D1F7C
 */

rootProject.name = "nxp-imx93-evk"
include("vee-port", "vee-port:front-panel", "vee-port:mock", "vee-port:image-generator", "app")

project(":vee-port:front-panel").projectDir = file("vee-port/extensions/front-panel")

include("vee-port:validation:core")
include("vee-port:validation:event-queue")
include("vee-port:validation:fs")
include("vee-port:validation:net")
// rio is experimental and is not public yet, so the VEE Port leaves it out.
// See README.md, "Experimental Features".
if (providers.gradleProperty("experimental.rio.enabled").orNull.toBoolean()) {
    include("vee-port:validation:rio")
}
include("vee-port:validation:crypto")
include("vee-port:validation:ssl")
include("vee-port:validation:ui")

val abstractionLayerCatalog = file("linux-abstraction-layer/gradle/libs.versions.toml")
if (!abstractionLayerCatalog.exists()) {
    throw GradleException(
        """

        The Git submodule `linux-abstraction-layer` is missing.
        The build cannot read `${abstractionLayerCatalog.path}`.

        Run this command at the root of the repository:

            git submodule update --init --recursive

        """.trimIndent()
    )
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libsOverride") {
            from(files(abstractionLayerCatalog))
        }
    }
}
