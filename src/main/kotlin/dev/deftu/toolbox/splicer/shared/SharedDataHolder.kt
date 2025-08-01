package dev.deftu.toolbox.splicer.shared

import org.gradle.api.Project
import org.gradle.api.invocation.Gradle
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType

inline fun <reified T : Any> Gradle.createDataHolder(vararg args: Any): T {
    return SharedDataHolder.createOn(this, *args)
}

inline fun <reified T : Any> Gradle.getDataHolder(): T {
    return SharedDataHolder.getFrom(this)
}

abstract class SharedDataHolder<T>(
    private val data: MutableMap<String, T> = mutableMapOf()
) : MutableMap<String, T> by data {

    companion object {

        @JvmStatic
        inline fun <reified T : Any> createOn(gradle: Gradle, vararg args: Any): T {
            return gradle.extensions.create<T>(requireNotNull(T::class.simpleName) { "SharedDataHolder requires a type name" }, *args)
        }

        @JvmStatic
        inline fun <reified T : Any> getFrom(gradle: Gradle): T {
            return gradle.extensions.getByType<T>()
        }

    }

    operator fun get(key: Project): T? {
        return data[key.path]
    }

}
