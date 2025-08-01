package dev.deftu.toolbox.splicer.settings

import dev.deftu.toolbox.splicer.ProjectReference
import dev.deftu.toolbox.splicer.node.GraphDataHolder
import dev.deftu.toolbox.splicer.node.builder.GraphBuilder
import dev.deftu.toolbox.splicer.node.builder.GraphBuilderImpl
import dev.deftu.toolbox.splicer.shared.createDataHolder
import org.gradle.api.initialization.Settings
import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

abstract class SplicerSettingsExtensionImpl @Inject constructor(
    private val settings: Settings,
    private val objects: ObjectFactory
) : SplicerSettingsExtension(objects) {
    val dataHolder = settings.gradle.createDataHolder<GraphDataHolder>()

    override fun create(project: ProjectReference, builder: GraphBuilder) {
        GraphBuilderImpl.get(builder).create(this, project, settings)
    }
}
