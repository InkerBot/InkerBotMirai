plugins {
    kotlin("jvm") version "1.5.10"
    id ("com.github.johnrengelman.shadow").version("7.0.0")
}

group = "bot.inker.mirai"
version = "1.0-SNAPSHOT"
description = "InkerBot Mirai Provider."
val mainClass = "bot.inker.mirai.MiraiCore"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("bot.inker:api:1.0-SNAPSHOT")
    api("net.mamoe:mirai-core:2.9.1")
}

tasks.processResources {
    filesMatching("META-INF/plugin.json") {
        expand(mapOf(
            "pluginName" to project.name,
            "pluginVersion" to project.version,
            "pluginDescribe" to project.description,
            "pluginMainClass" to mainClass,
        ))
    }
}

tasks.shadowJar {
    archiveVersion.set("")
    archiveClassifier.set("app")

    dependencies{
        exclude("org.jetbrains.kotlinx:kotlinx-coroutines-core")
        exclude("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8")
        exclude("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm")
        exclude("org.jetbrains.kotlinx:kotlinx-coroutines-core")
        exclude("org.jetbrains.kotlin")
        exclude("org.slf4j")
        exclude("com.squareup.okio")
        exclude("com.squareup.okhttp3")
        exclude("org.apache.logging.log4j")
        exclude("org.jetbrains:annotations")
        exclude {
            (excludes.contains(it.moduleGroup) || excludes.contains(it.moduleGroup+":")
                    || excludes.contains(":"+it.moduleName) || excludes.contains(it.moduleGroup+":"+it.moduleName))
        }
    }
}

tasks.assemble{
    dependsOn(tasks.shadowJar)
}