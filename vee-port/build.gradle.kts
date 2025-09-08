/*
 * Kotlin
 *
 * Copyright 2025 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */

import java.io.FileOutputStream
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

plugins {
    id("com.microej.gradle.veeport") version libs.versions.microej.sdk
}

configurations {
    create("compileClasspath")
}

microej {
    skippedCheckers = "changelog,readme,license"
    withIvyDescriptor()
}

dependencies {
    microejArchitecture(libs.architecture)

    microejPack(libs.pack.ui.architecture)

    // Packs are fetched from linux-abstraction-layer/gradle/libs.versions.toml
    microejPack(libsOverride.pack.device)
    microejPack(libsOverride.pack.ecom.network)
    microejPack(libsOverride.pack.event)
    microejPack(libsOverride.pack.fs)
    microejPack(libsOverride.pack.net)

    microejFrontPanel(project(":vee-port:front-panel"))

    microejMock("com.is2t.microej.deploy:jenkins:1.0.2")
}

tasks.register<Copy>("copyBspScripts") {
    from("bsp")
    into("../linux-abstraction-layer/vee/scripts")
}

tasks.getByName("buildVeePort").dependsOn("copyBspScripts")
tasks.getByName("buildVeePortConfiguration").dependsOn("copyBspScripts")

tasks.register("updateIvyDescriptor") {
    doLast {
        val ivyFile = layout.buildDirectory.file("ivy.xml").get().asFile.absolutePath

        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val document = builder.parse(ivyFile)
        val publicationsElements = document.getElementsByTagName("publications")
        if (publicationsElements.length == 1) {
            val publications = publicationsElements.item(0)

            while (publications.hasChildNodes()) {
                publications.removeChild(publications.firstChild)
            }

            var newPublication = document.createElement("artifact")
            newPublication.setAttribute("name", "vee-port")
            newPublication.setAttribute("type", "zip")
            newPublication.setAttribute("ext", "zip")
            newPublication.setAttribute("conf", "default")
            publications.appendChild(newPublication)

            newPublication = document.createElement("artifact")
            newPublication.setAttribute("name", "vee-port")
            newPublication.setAttribute("type", "pom")
            newPublication.setAttribute("ext", "pom")
            newPublication.setAttribute("conf", "dist")
            publications.appendChild(newPublication)

            newPublication = document.createElement("artifact")
            newPublication.setAttribute("name", "vee-port")
            newPublication.setAttribute("type", "module")
            newPublication.setAttribute("ext", "module")
            newPublication.setAttribute("conf", "dist")
            publications.appendChild(newPublication)

            val transformerFactory = TransformerFactory.newInstance()
            val transformer = transformerFactory.newTransformer()
            val source = DOMSource(document)
            val result = StreamResult(FileOutputStream(ivyFile))
            transformer.transform(source, result)
        }
    }
}

tasks.getByName("generateIvyDescriptor").finalizedBy("updateIvyDescriptor")
