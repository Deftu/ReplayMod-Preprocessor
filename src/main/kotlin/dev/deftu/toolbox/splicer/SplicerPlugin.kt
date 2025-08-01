package dev.deftu.toolbox.splicer

import dev.deftu.toolbox.splicer.project.SplicerProjectPlugin
import dev.deftu.toolbox.splicer.settings.SplicerSettingsPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.initialization.Settings
import org.gradle.api.plugins.PluginAware

class SplicerPlugin : Plugin<PluginAware> {
    override fun apply(target: PluginAware) {
        when (target) {
            is Settings -> SplicerSettingsPlugin.apply(target)
            is Project -> SplicerProjectPlugin.apply(target)
            else -> throw IllegalArgumentException("SplicerPlugin can only be applied to a Project")
        }
    }
}
