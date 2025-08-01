package dev.deftu.toolbox.splicer

import org.gradle.api.UnknownProjectException
import org.gradle.api.initialization.ProjectDescriptor
import org.gradle.api.initialization.Settings
import org.gradle.api.provider.Provider

/**
 * A reference to a Gradle project in some form.
 * This can be represented as a [String], a [Provider][org.gradle.api.provider.Provider],
 * or a [ProjectDescriptor][org.gradle.api.initialization.ProjectDescriptor].
 */
typealias ProjectReference = Any

fun ProjectReference.resolve(settings: Settings): ProjectDescriptor {
    return when (this) {
        is ProjectDescriptor -> this
        is CharSequence -> try {
            settings.project(":${trimStart(':')}").include(settings)
        } catch (e: UnknownProjectException) {
            include(settings)
        }

        is Provider<*> -> get().resolve(settings)
        else -> throw IllegalArgumentException("Unsupported project reference type: ${this::class.java}")
    }
}

private fun ProjectReference.include(settings: Settings): ProjectDescriptor {
    return when (this) {
        is ProjectDescriptor -> this.include(settings)
        is CharSequence -> {
            settings.include(this.toString())
            settings.project(":${this.trimStart(':')}").include(settings)
        }

        is Provider<*> -> get().include(settings)
        else -> throw IllegalArgumentException("Unsupported project reference type: ${this::class.java}")
    }
}

private fun ProjectDescriptor.include(settings: Settings): ProjectDescriptor {
    if (this.path != ":") {
        settings.include(this.path.removePrefix(":"))
    }

    return this
}
