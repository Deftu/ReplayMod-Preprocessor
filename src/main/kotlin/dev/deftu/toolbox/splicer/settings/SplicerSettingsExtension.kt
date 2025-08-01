package dev.deftu.toolbox.splicer.settings

import dev.deftu.toolbox.splicer.ProjectReference
import dev.deftu.toolbox.splicer.node.builder.GraphBuilder
import dev.deftu.toolbox.splicer.node.builder.GraphBuilderImpl
import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

/**
 * Defines a hefty DSL for defining preprocessed subprojects in the settings.gradle.kts file.
 *
 * ```
 * include(":my-1.21.6-project")
 *
 * splicer {
 *      create(rootProject) {
 *          rootBuildScript = file("root.gradle.kts")
 *          centalBuildScript = file("build.gradle.kts")
 *          autoProjectsDirectory = file("preprocessed")
 *
 *          root(node("1.21.8-neoforge", 1_21_08))
 *              .to(node("1.21.8-fabric", 1_21_08, ExtraMappings.Convention()))
 *              .to(node("1.21.7-fabric", 1_21_07, ExtraMappings.Directory("my_mappings")))
 *              .to(node("1.21.7-neoforge", 1_21_07, ExtraMappings.File(file("version_mappings/my_mappings.txt"))))
 *              .to(node("1.21.6-neoforge", 1_21_06) {
 *                  project = project(":my-1.21.6-project")
 *              })
 *      }
 * }
 * ```
 */
abstract class SplicerSettingsExtension @Inject constructor(
    private val objects: ObjectFactory
) {
    abstract fun create(project: ProjectReference, builder: GraphBuilder)

    fun create(
        project: ProjectReference,
        action: Action<GraphBuilder>
    ) {
        val builder = objects.newInstance(GraphBuilderImpl::class.java)
        action.execute(builder)
        create(project, builder)
    }
}
