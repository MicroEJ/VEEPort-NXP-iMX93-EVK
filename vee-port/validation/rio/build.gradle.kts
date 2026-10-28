/*
 * Kotlin
 *
 * Copyright 2026 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 *
 * Build: 7E4D1F7C
 */

import com.microej.gradle.plugins.TestMode
import com.microej.gradle.plugins.TestTarget

plugins {
    id("com.microej.gradle.testsuite")
}

microej {
    architectureUsage = System.getProperty("com.microej.architecture.usage") ?: "eval"
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

                implementation(libs.testsuite.rio)
                implementation(libs.testsuite.net.rio)
            }

            targets {
                all {
                    testTask.configure {
                        filter {
                            // rio-testsuite JUnit wrappers.
                            includeTestsMatching("*AllTests_*")
                            // net-rio-testsuite: main()/CheckHelper tests mirrored
                            // from the java.net net testsuite (same FQNs), in the
                            // group and integration.group packages.
                            includeTestsMatching("com.microej.net.test.*Test*")
                            // do not pass standalone tests, only group tests
                            // (mirrors the net runner; peers are keyed on the group class)
                            excludeTestsMatching("com.microej.net.test.standalone*")
                            excludeTestsMatching("*SingleTest*")
                            // do not embed inner classes as test classes
                            excludeTestsMatching("*$*")
                        }
                        doFirst {
                            systemProperties = mapOf(
                                "microej.testsuite.properties.deploy.bsp.microejscript" to "true",
                                "microej.testsuite.properties.microejtool.deploy.name" to "deployToolBSPRun",
                                "microej.testsuite.properties.launch.test.trace.file" to "true",
                                "microej.testsuite.properties.testsuite.trace.timeout" to "120",
                                "microej.testsuite.verbose.level" to "verbose",
                                // Fit TestConcurrentPoll's parallel workers.
                                "microej.testsuite.properties.core.memory.threads.size" to "16",
                                "microej.testsuite.properties.core.memory.threads.pool.size" to "60",
                            )
                        }
                    }
                }
            }
        }
    }
}
