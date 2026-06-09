plugins {
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    testImplementation(project(":components:test-fake-server"))
    testImplementation(project(":components:test-server"))
}
