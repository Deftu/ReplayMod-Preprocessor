/* Copyright (C) 2019 Jonas Herzig <me@johni0702.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

plugins {
    kotlin("jvm") version(libs.versions.kotlin)
    `kotlin-dsl`

    with(libs.versions.dgt) {
        id("dev.deftu.gradle.tools.repo") version(this)
        id("dev.deftu.gradle.tools.configure") version(this)
        id("dev.deftu.gradle.tools.publishing.maven") version(this)
        id("dev.deftu.gradle.tools.publishing.github") version(this)
    }
}

toolkitMavenPublishing {
    setupPublication.set(false)
}

repositories {
    maven("https://jitpack.io/")
    maven("https://maven.fabricmc.net/")
    maven("https://maven.minecraftforge.net/")
    maven("https://maven.architectury.dev/")
    maven("https://maven.jab125.dev/")

    mavenCentral()
    gradlePluginPortal()
    mavenLocal()
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())
    implementation("dev.deftu:remap:0.3.0")
    implementation("net.fabricmc:mapping-io:0.6.1")
}

gradlePlugin {
    plugins {
        // New plugin~!
        register("splicer") {
            id = "dev.deftu.toolbox.splicer"
            implementationClass = "dev.deftu.toolbox.splicer.SplicerPlugin"
        }

        // Preprocess
        register("preprocess") {
            id = "dev.deftu.gradle.preprocess"
            implementationClass = "com.replaymod.gradle.preprocess.PreprocessPlugin"
        }

        register("preprocess-root") {
            id = "dev.deftu.gradle.preprocess-root"
            implementationClass = "com.replaymod.gradle.preprocess.RootPreprocessPlugin"
        }
    }
}

tasks {
    named<Jar>("jar") {
        from("LICENSE")

        manifest {
            attributes["Implementation-Version"] = version
        }
    }
}
