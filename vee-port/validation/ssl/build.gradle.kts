/*
 * Kotlin
 *
 * Copyright 2025 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
import com.microej.gradle.plugins.TestMode
import com.microej.gradle.plugins.TestTarget

plugins {
    id("com.microej.gradle.testsuite")
}

microej {
    architectureUsage = System.getProperty("com.microej.architecture.usage") ?: "eval" // or "prod"
    skippedCheckers = "changelog,readme,license"
}

dependencies {
    microejVee(project(":vee-port"))
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            microej.useMicroejTestEngine(this, TestTarget.EMB, TestMode.MAIN)

            dependencies {
                implementation(project())
                implementation(libs.junit)
                implementation(libs.junit.platform)

                implementation(libs.api.edc)
                implementation(libs.api.bon)
                implementation(libs.api.ssl)
                implementation(libs.api.net)
                implementation(libs.testsuite.ssl)
                implementation(libs.testsuite.ssl.openjdk)
                // Add the following dependency if running the testsuite over Wi-Fi.
                // implementation(libs.net.wifi.testsuite)
            }

            targets {
                all {
                    testTask.configure {
                        filter {
                            includeTestsMatching("Test*")
                            excludeTestsMatching("*SingleTest*")
                            // PKI tests are used to check certificate chain validation policies.
                            excludeTestsMatching("*TestHandshakePki*")
                            // Utility class that contains no tests.
                            excludeTestsMatching("TestUtils")
                            // Timeout
                            excludeTestsMatching("TestSocketCreation")
                            excludeTestsMatching("*$*")
                        }
                    }
                }
            }
        }
    }
}
