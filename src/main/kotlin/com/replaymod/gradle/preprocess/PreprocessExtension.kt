package com.replaymod.gradle.preprocess

import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.mapProperty
import org.gradle.kotlin.dsl.property
import java.io.File
import java.util.function.Predicate

open class PreprocessExtension(objects: ObjectFactory, mcVersion: Int) {
    val vars = objects.mapProperty<String, Int>().convention(
        mutableMapOf(
            "MC" to mcVersion
        )
    )
    val keywords = objects.mapProperty<String, Keywords>().convention(
        mutableMapOf(
            ".java" to PreprocessTask.DEFAULT_KEYWORDS,
            ".kt" to PreprocessTask.DEFAULT_KEYWORDS,
            ".gradle" to PreprocessTask.DEFAULT_KEYWORDS,
            ".json" to PreprocessTask.DEFAULT_KEYWORDS,
            ".mcmeta" to PreprocessTask.DEFAULT_KEYWORDS,
            ".cfg" to PreprocessTask.CFG_KEYWORDS
        )
    )
    val patternAnnotation = objects.property<String>()
    val manageImports = objects.property<Boolean>()

    /**
     * A filter to determine which Kotlin files should be processed.
     * By default, all files are included (`{ true }`).
     * This property can be customized to exclude certain files, helping to prevent
     * invalid task dependencies when working with code generation tools.
     */
    val kotlinFilter = objects.property<Predicate<File>>().convention { true }

    /**
     * A filter to determine which Java files should be processed.
     * By default, all files are included (`{ true }`).
     * This property can be customized to exclude certain files, helping to prevent
     * invalid task dependencies when working with code generation tools.
     */
    val javaFilter = objects.property<Predicate<File>>().convention { true }
}