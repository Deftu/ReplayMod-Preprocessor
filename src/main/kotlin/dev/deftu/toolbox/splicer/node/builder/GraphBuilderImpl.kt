package dev.deftu.toolbox.splicer.node.builder

import dev.deftu.toolbox.splicer.ProjectReference
import dev.deftu.toolbox.splicer.resolve
import dev.deftu.toolbox.splicer.settings.SplicerSettingsExtensionImpl
import org.gradle.api.Action
import org.gradle.api.initialization.Settings
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Provider
import java.io.File
import javax.inject.Inject

abstract class GraphBuilderImpl @Inject constructor(
    private val objects: ObjectFactory
) : GraphBuilder() {

    companion object {
        fun get(abstract: GraphBuilder): GraphBuilderImpl {
            if (abstract !is GraphBuilderImpl) {
                throw IllegalArgumentException("GraphBuilder must be an instance of GraphBuilderImpl")
            }

            return abstract
        }
    }

    private lateinit var rootNode: NodeBuilder

    override fun root(node: NodeBuilder): NodeBuilder {
        rootNode = node
        return rootNode
    }

    override fun node(name: String, version: Int, action: Action<NodeBuilder>?): NodeBuilder {
        val builder = objects.newInstance(NodeBuilderImpl::class.java, name, version)
        action?.execute(builder)
        return builder
    }

    override fun dumpTree() {
        // Prints out the entire preprocessing graph in a human-readable format
        println("Preprocessing Graph:")
        fun printNode(node: NodeBuilder, indent: String = "") {
            println("$indent- ${node.project.get()}")
            (node as NodeBuilderImpl).links.forEach { child ->
                printNode(child, "$indent  ")
            }
        }

        printNode(rootNode)
    }

    fun create(
        extension: SplicerSettingsExtensionImpl,
        project: ProjectReference,
        settings: Settings,
    ) {
        val descriptor = project.resolve(settings)
        require(extension.dataHolder.putIfAbsent(descriptor.path, this) == null) { "'${descriptor.path}' is already in registered in the preprocessing graph" }
        println("Hooray! We are creating a preprocessing graph for '${descriptor.path}'")

        settings.gradle.settingsEvaluated {
            with(resolveCoreProjectDefinition(settings)) {
                if (!this.exists()) {
                    require(this.parentFile.exists() || this.parentFile.mkdirs()) {
                        "Failed to create parent directory for core project definition: ${this.parentFile}"
                    }

                    this.createNewFile()
                }
            }
        }
    }

}
