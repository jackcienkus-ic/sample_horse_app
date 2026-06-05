plugins {
    id("org.jetbrains.kotlin.plugin.serialization")
}

kotlin {
    jvmToolchain(25)
}

dependencies {
    implementation(project(":components:watchduty"))
}