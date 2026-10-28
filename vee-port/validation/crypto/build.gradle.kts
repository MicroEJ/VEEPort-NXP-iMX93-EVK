/*
 * Kotlin
 *
 * Copyright 2025-2026 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 *
 * Build: 7E4D1F7C
 */
import com.microej.gradle.plugins.TestMode
import com.microej.gradle.plugins.TestTarget
import java.util.Properties
import org.gradle.internal.os.OperatingSystem

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

// Load VEE Port configuration to resolve the BSP scripts directory
fun loadScriptsDir(): File {
    val configFile = file("${rootProject.projectDir}/vee-port/configuration.properties")
    val props = Properties().apply {
        configFile.inputStream().use { fis -> load(fis) }
    }
    val bspRootDir = props.getProperty("bsp.root.dir").replace("\${project.parent.dir}", "..") + "/"
    return file("${rootProject.projectDir}/vee-port/${bspRootDir}${props.getProperty("bsp.microejscript.relative.dir")}")
}

// Copy the test keystore to the BSP scripts directory so that run.sh can deploy it
val copyTestKeystore by tasks.registering(Copy::class) {
    dependsOn("loadTestVee", "compileTestJava")
    from(layout.buildDirectory.file("classes/java/test/test-keystore.p12"))
    into(loadScriptsDir())
}

// Copy the OP-TEE PKCS#11 token provisioning script alongside the keystore.
// It is a test-only fixture (hardcoded aliases, PIN, and key material match the
// test suite) and therefore lives next to the test suite rather than in the
// generic BSP. deploy_keystore.sh invokes it on the board when
// LLCRYPTO_KEYSTORE_TYPE=PKCS11.
val copyProvisioningScripts by tasks.registering(Copy::class) {
    from("scripts")
    include("setup-*-token.sh")
    into(loadScriptsDir())
    // Set file permissions to make it executable
    filePermissions { unix("rwxr-xr-x") }
}

val deployKeystore by tasks.registering(Exec::class) {
    // deploy_keystore.{sh,bat} live in vee-port/bsp/ and are staged into the
    // scripts dir by :vee-port:copyBspScripts; make the dependency explicit so
    // the staging happens even when the VEE port has already been built.
    dependsOn(":vee-port:copyBspScripts", copyTestKeystore, copyProvisioningScripts)
    workingDir = loadScriptsDir()
    if (OperatingSystem.current().isWindows) {
        commandLine("cmd", "/c", workingDir.resolve("deploy_keystore.bat").absolutePath)
    } else if (OperatingSystem.current().isLinux) {
        commandLine("dos2unix", "./*.sh")
        commandLine("bash", "deploy_keystore.sh")
    }
}

tasks.named("test") {
    dependsOn(deployKeystore)
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
                implementation(libs.testsuite.crypto)
                implementation(libs.api.crypto)
            }

            targets {
                all {
                    testTask.configure {
                        filter {
                            excludeTestsMatching("*AllTestClasses")
                            excludeTestsMatching("*SingleTest*")
                            // do not embed inner classes as test classes
                            excludeTestsMatching("*$*")
                        }
                    }
                }
            }
        }
    }
}
