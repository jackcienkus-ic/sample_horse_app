plugins {
    kotlin("jvm") version "2.3.20"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.3.20" apply false
}

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
}

subprojects {
    if (name == "applications" || name == "components") return@subprojects
    apply(plugin = "kotlin")
    configure<org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension> {
        jvmToolchain(21)
    }
    repositories {
        mavenCentral()
    }
    dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.11.0")
        implementation("org.slf4j:slf4j-api:2.0.17")
        testImplementation(kotlin("test-junit"))
    }
}
