package dev.deftu.toolbox.splicer.node.builder

import dev.deftu.toolbox.splicer.node.NodeMetadataKey
import javax.inject.Inject

abstract class NodeBuilderImpl @Inject constructor(
    private val name: String,
    private val version: Int,
) : NodeBuilder() {

    internal val links = mutableListOf<NodeBuilder>()
    internal val metadata = mutableMapOf<NodeMetadataKey<*>, Any>()

    init {
        project.convention(name)
    }

    override fun to(node: NodeBuilder): NodeBuilder {
        links.add(node)
        return node
    }

    override fun <T> set(key: NodeMetadataKey<T>, value: T): NodeBuilder {
        metadata[key] = value as Any
        return this
    }

    override fun <T> get(key: NodeMetadataKey<T>): T? {
        @Suppress("UNCHECKED_CAST")
        return metadata[key] as? T
    }

}
