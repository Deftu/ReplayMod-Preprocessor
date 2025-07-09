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
    groovy
    kotlin("jvm") version("2.0.20")
    `kotlin-dsl`
    val dgtVersion = "2.38.0"
    id("dev.deftu.gradle.tools") version(dgtVersion)
    id("dev.deftu.gradle.tools.publishing.maven") version(dgtVersion)
}

val kotestVersion: String by project.extra

java {
    withSourcesJar()
}

repositories {
    mavenLocal()
    mavenCentral()
    maven(url = "https://jitpack.io/")
    maven(url = "https://maven.fabricmc.net/")
    maven(url = "https://maven.deftu.dev/releases/")
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())
    implementation("dev.deftu:remap:0.3.0")
    implementation("net.fabricmc:mapping-io:0.6.1")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
}

gradlePlugin {
    plugins {
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
    withType<Test> {
        useJUnitPlatform()
    }

    jar {
        manifest {
            attributes["Implementation-Version"] = version
        }
    }
}
