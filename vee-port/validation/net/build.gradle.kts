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

                implementation(libs.testsuite.net)
                implementation(libs.testsuite.net.openjdk)
                implementation(libs.api.net)

                // Add the following dependency if running the testsuite over Wi-Fi.
                // implementation(libs.net.wifi.testsuite)
            }

            targets {
                all {
                    testTask.configure {
                        filter {
                            includeTestsMatching("Test*")
                            excludeTestsMatching("*AllTestClasses")
                            excludeTestsMatching("*SingleTest*")
                            // do not embed inner classes as test classes
                            excludeTestsMatching("*$*")
                            // do not pass standalone tests, only group tests
                            excludeTestsMatching("com.microej.net.test.standalone*")

                            // test not relevant for linux, we run only TestNetAdditionalLinux, not both
                            excludeTestsMatching("com.microej.net.test.group.TestNetAdditionalWindows")
                            // test not relevant for linux
                            excludeTestsMatching("com.microej.net.test.group.TestDatagramSocketSetBufferSize")
                            // test setup issue (no ipv6 support in linux distro), to be run again
                            excludeTestsMatching("com.microej.net.test.group.dual.TestDualStackSocket")
                            excludeTestsMatching("com.microej.net.test.group.dual.TestDualStackDatagram")
                            excludeTestsMatching("com.microej.net.test.group.dual.TestDualStackServerSocket")
                            // This test may not work if the system does not apply exactly the values given to the set method (e.g. on Linux).
                            excludeTestsMatching("TestServerSocketAdditional2")
                            // This test may not work if the system does not apply exactly the values given to the set method (e.g. on Linux).
                            excludeTestsMatching("TestSocketOptionsSendRecvBuffer2")
                            // Redundant tests
                            excludeTestsMatching("TestClose")
                            excludeTestsMatching("TestAfterClose")
                            excludeTestsMatching("Test")
                            excludeTestsMatching("TestTcpNoDelay")
                            // No main method
                            excludeTestsMatching("Tests")
                            // To be run as a single test
                            excludeTestsMatching("TestSocketReliability01")
                            // Unexpected multicast message
                            excludeTestsMatching("TestNetOpenjdkPromiscuous")
                            // Wrong exception on socket close.
                            excludeTestsMatching("TestNetOpenjdkSocketClosedException")
                            // Hostname issue
                            excludeTestsMatching("TestNetOpenjdkB6469803")
                            excludeTestsMatching("TestNetAdditionalLinux")
                        }
                    }
                }
            }
        }
    }
}
