plugins {
    id("org.jetbrains.kotlin.plugin.serialization")
    kotlin("jvm") version "2.3.20"

}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(project(":components:test-server"))
}