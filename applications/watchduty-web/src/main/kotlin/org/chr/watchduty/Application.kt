package org.chr.watchduty

import io.ktor.http.ContentType
import java.util.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*



class Application() {

    // todo -
    // introduce ktor
    // introduce the database

    fun extracted(baseUrl: String): List<WatchDutyEvent> {
        val service = WatchDutyService(baseUrl)
        val latest = service.latestHTTP()
        println("Fetched latest watch duty data")
        return latest
    }
}

fun main() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    val latest = Application().extracted("https://api.watchduty.org")
    println(latest)

    embeddedServer(Netty, 8080) {
        install(ContentNegotiation) {
            json()
        }
        routing {
            get("/fires") {
                val fires = WatchDutyService("https://api.watchduty.org").latestHTTP()
                val filteredFires = fires.filter {
                    EventClassification().isInColorado(it.lat!!, it.lng!!, 50.0)
                }
                for (fire in filteredFires) {
                    fire.county=EventClassification().assignCounty(fire)
                }
                call.respondText(
                    contentType = ContentType.parse("text/html"),
                    text = filteredFires.fireAsTable()
                )
            }
        }
    }.start(wait = true)
}
