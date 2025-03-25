/*
 * Kotlin
 *
 * Copyright 2025 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */

import java.util.*

plugins {
    id("com.microej.gradle.veeport") version libs.versions.microej.sdk
}

microej {
    skippedCheckers = "changelog,readme,license"
}

dependencies {
    microejArchitecture(libs.architecture)

    microejPack(libs.pack.ui.architecture)

//    Packs are fetched from Linux-AbstractionLayer/gradle/libs.versions.toml
    microejPack(libsOverride.pack.device)
    microejPack(libsOverride.pack.ecom.network)
    microejPack(libsOverride.pack.fs)
    microejPack(libsOverride.pack.net)

    microejFrontPanel(project(":vee-port:front-panel"))

//    microejMock(project(":vee-port:mock"))
    microejMock("com.is2t.microej.deploy:jenkins:1.0.2")

//    microejTool(project(":vee-port:image-generator"))
}

tasks.register<Copy>("copyBspScripts") {
    from("bsp")
    into("../Linux-abstractionlayer/vee/scripts")
}

tasks.getByName("buildVeePort").dependsOn("copyBspScripts")
tasks.getByName("buildVeePortConfiguration").dependsOn("copyBspScripts")
