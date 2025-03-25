/*
 * Kotlin
 *
 * Copyright 2024 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */

plugins {
    id("com.microej.gradle.application") version libs.versions.microej.sdk
}

microej {
    skippedCheckers = "readme,changelog,license,nullanalysis"
}

microej {
    applicationEntryPoint = "com.mycompany.myapplication.Main"
    architectureUsage = System.getProperty("com.microej.architecture.usage") ?: "eval" // or "prod"
}

dependencies {
    implementation(libs.api.edc)
    // Remove the API dependency when you remove the related pack
    implementation(libs.api.microui)
    implementation(libs.api.fs)
    implementation(libs.api.net)
    implementation(libs.api.ssl)
    microejVee(project(":vee-port"))
}
