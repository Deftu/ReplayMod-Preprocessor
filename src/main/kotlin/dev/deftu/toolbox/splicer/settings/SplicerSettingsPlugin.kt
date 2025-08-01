package dev.deftu.toolbox.splicer.settings

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings

object SplicerSettingsPlugin : Plugin<Settings> {

    override fun apply(target: Settings) {
        target.extensions.create(SplicerSettingsExtension::class.java, "splicer", SplicerSettingsExtensionImpl::class.java, target)
    }

}
