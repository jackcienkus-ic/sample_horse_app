pluginManagement {
    val kotlinVersion = "2.3.20"
    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion
    }
}

rootProject.name = "sample_horse_app"

include("applications:watchduty-web")
include("components:test-fake-server")
include("components:test-server")
include("components:watchduty")