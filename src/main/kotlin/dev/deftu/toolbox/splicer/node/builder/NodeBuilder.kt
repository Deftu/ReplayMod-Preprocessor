package dev.deftu.toolbox.splicer.node.builder

import dev.deftu.toolbox.splicer.ProjectReference
import dev.deftu.toolbox.splicer.node.NodeMetadataKey
import org.gradle.api.provider.Property

abstract class NodeBuilder {

    abstract val project: Property<ProjectReference>

    abstract fun to(node: NodeBuilder): NodeBuilder

    abstract fun <T> set(key: NodeMetadataKey<T>, value: T): NodeBuilder

    abstract fun <T> get(key: NodeMetadataKey<T>): T?

    fun composite(vararg nodes: NodeBuilder): NodeBuilder {
        for (node in nodes) {
            this.to(node)
        }

        return this
    }

}
