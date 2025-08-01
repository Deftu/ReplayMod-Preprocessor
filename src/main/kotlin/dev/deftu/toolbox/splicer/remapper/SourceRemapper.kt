package dev.deftu.toolbox.splicer.remapper

import java.io.File

/**
 * A remapper capable of transforming source code using a mapping set.
 */
interface SourceRemapper {

    fun remapSources(inputDirectory: File, outputDirectory: File)

    /**
     * Provides remapper-specific context.
     *
     * For example:
     * - a Lorenz MappingSet
     * - a Tiny mapping file
     * - a patterns config
     */
    fun <T> setHint(key: RemapperHint<T>, value: T)

    /**
     * Optionally set classpath roots if needed.
     */
    fun setClasspath(classpath: List<File>) {
    }

}
