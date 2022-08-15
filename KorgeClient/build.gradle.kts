import com.soywiz.korge.gradle.*

buildscript {
	val korgePluginVersion: String by project

	repositories {
		mavenLocal()
		mavenCentral()
		google()
		maven { url = uri("https://plugins.gradle.org/m2/") }
	}
	dependencies {
		classpath("com.soywiz.korlibs.korge.plugins:korge-gradle-plugin:$korgePluginVersion")
	}
}

apply<KorgeGradlePlugin>()

korge {
	id = "com.kietyo.multiplayer.korge_client.example"
	supportBox2d()
// To enable all targets at once

	//targetAll()

// To enable targets based on properties/environment variables
	//targetDefault()

// To selectively enable targets

	targetJvm()
	targetJs()
	targetDesktop()
	targetIos()
	targetAndroidIndirect() // targetAndroidDirect()

	serializationJson()


	val ktor_version = "1.6.6"
	project.dependencies.add("commonMainApi", "ch.qos.logback:logback-classic:1.2.3")
	project.dependencies.add("commonMainApi", "io.ktor:ktor-client-websockets:$ktor_version")
//	project.dependencies.add("commonMainApi", "com.kietyo.multiplayer.gamelogic:GameLogic:1.0.0")
//	project.dependencies.add("commonMainApi", "org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")
	project.dependencies.add("jvmMainApi", "io.ktor:ktor-client-cio:$ktor_version")
	project.dependencies.add("jsMainApi", "io.ktor:ktor-client-js:$ktor_version")
	project.dependencies.add("commonMainImplementation", project(":GameLogic"))

}