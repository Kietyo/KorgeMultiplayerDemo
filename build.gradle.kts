println("Setting up KorgeMultiplayer:build.gradle.kts")

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
}

plugins {
    val kotlin_version: String = "1.7.10"

    kotlin("jvm") version kotlin_version apply false
    kotlin("multiplatform") version kotlin_version apply false
    kotlin("plugin.serialization") version kotlin_version apply false

    println("Finished setting up plugins")
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
}

subprojects {
    println("Going through subprojects, name: ${this.name}")
}
