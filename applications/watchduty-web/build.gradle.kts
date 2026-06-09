plugins {
    id("org.jetbrains.kotlin.plugin.serialization")
    alias(ktorLibs.plugins.ktor)
}

application {
    mainClass = "io.ktor.server.jetty.jakarta.EngineMain"
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(project(":components:watchduty"))
    implementation(ktorLibs.server.config.yaml)
    implementation(ktorLibs.server.core)
    implementation(ktorLibs.server.freemarker)
    implementation(ktorLibs.server.jetty)
    implementation("ch.qos.logback:logback-classic:1.5.18")
    testImplementation(project(":components:test-fake-server"))
    testImplementation(project(":components:test-server"))
    testImplementation(ktorLibs.server.testHost)
}