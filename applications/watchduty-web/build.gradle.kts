plugins {
    id("org.jetbrains.kotlin.plugin.serialization")
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(project(":components:watchduty"))
    testImplementation(project(":components:test-fake-server"))
    testImplementation(project(":components:test-server"))
}