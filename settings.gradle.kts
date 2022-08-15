println("Setting up KorgeMultiplayer:settings.gradle.kts")

pluginManagement {
    val kotlin_version: String by settings
    plugins {
        kotlin("jvm") version kotlin_version apply false
        kotlin("multiplatform") version kotlin_version apply false
        kotlin("plugin.serialization") version kotlin_version apply false

        println("Finished setting up plugins")
    }
}

rootProject.name = "KorgeMultiplayer"

include(":GameLogic")
include(":KorgeClient")
include(":Server")