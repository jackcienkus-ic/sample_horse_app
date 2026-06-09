pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
    versionCatalogs {
        create("ktorLibs").from("io.ktor:ktor-version-catalog:3.5.0")
    }
}

rootProject.name = "sample_horse_app"

include("applications:watchduty-web")
include("components:test-fake-server")
include("components:test-server")
include("components:watchduty")
