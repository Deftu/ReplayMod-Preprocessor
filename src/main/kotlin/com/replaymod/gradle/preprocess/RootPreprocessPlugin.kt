package com.replaymod.gradle.preprocess

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

class RootPreprocessPlugin : Plugin<Project> {
    val version: String
        get() {
            return javaClass.`package`.implementationVersion ?: "0.0.0"
        }

    override fun apply(project: Project) {
        project.logger.lifecycle("Preprocessor $version")
        project.extensions.create("preprocess", RootPreprocessExtension::class, project, project.objects)
    }
}
