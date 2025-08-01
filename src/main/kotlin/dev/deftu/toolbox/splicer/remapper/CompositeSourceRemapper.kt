package dev.deftu.toolbox.splicer.remapper

import java.io.File
import kotlin.io.path.createTempDirectory

class CompositeSourceRemapper(private val remappers: List<SourceRemapper>) : SourceRemapper {

    override fun remapSources(inputDirectory: File, outputDirectory: File) {
        require(inputDirectory.isDirectory) { "Input must be a directory" }
        require(outputDirectory.isDirectory || outputDirectory.mkdirs()) { "Output must be a directory and must be created if it does not exist" }

        var currentInput = inputDirectory
        for ((index, remapper) in remappers.withIndex()) {
            val intermediateOutput = if (index == remappers.lastIndex) {
                outputDirectory
            } else {
                createTempDirectory("splicer-intermediate-${index}").toFile()
            }

            remapper.remapSources(currentInput, intermediateOutput)
            currentInput = intermediateOutput
        }
    }

    override fun <T> setHint(key: RemapperHint<T>, value: T) {
        remappers.forEach { it.setHint(key, value) }
    }

    override fun setClasspath(classpath: List<File>) {
        remappers.forEach { it.setClasspath(classpath) }
    }

}
