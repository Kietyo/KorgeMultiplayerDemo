println("Setting up KorgeMultiplayer:build.gradle.kts")

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
        println("Finish setting up repositories for buildscript.")
    }
}

plugins {
    kotlin("jvm") apply false
    kotlin("multiplatform") apply false
    kotlin("plugin.serialization") apply false

    println("Finished setting up plugins")
}
