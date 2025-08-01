package dev.deftu.toolbox.splicer

import java.io.Serializable

data class KeywordDefinitions(
    val disableRemap: String,
    val enableRemap: String,
    val ifDirective: String,
    val ifDefDirective: String,
    val elseIfDirective: String,
    val elseDirective: String,
    val endIfDirective: String,
    val evalDirective: String,
) : Serializable
