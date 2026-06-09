package org.chr.watchduty

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.math.cos

fun Application.configureRouting() {
    val baseUrl = environment.config.propertyOrNull("watchduty.baseUrl")?.getString()
        ?: "https://api.watchduty.org"
    configureRouting(WatchDutyService(baseUrl))
}

fun Application.configureRouting(service: WatchDutyService) {
    routing {
        get("/") {
            call.respondText("Hello, World!")
        }

        get("/html-freemarker") {
            call.respond(FreeMarkerContent("index.ftl", mapOf("data" to IndexData(listOf(1, 2, 3))), ""))
        }

        get("/fires") {
            val region = call.request.queryParameters["region"] ?: "0.0"
            val mileDiff = region.toDoubleOrNull() ?: return@get call.respondText(
                "Missing or invalid parameter. Must be a valid Double."
            )

            val events = service.latest()
            val fires = events
                .filter { event ->
                    val lat = event.lat
                    val lng = event.lng
                    val latExpansion = mileDiff / 69.1
                    val lngExpansion = mileDiff / (69.17 * cos(Math.toRadians(lat)))
                    lat in (36.99 - latExpansion)..(41.00 + latExpansion) &&
                            lng in (-109.05 - lngExpansion)..(-102.05 + lngExpansion)
                }
                .map { event ->
                    Fire(
                        name = event.name ?: "Unknown",
                        size = event.acreage,
                        lat = event.lat,
                        lng = event.lng,
                        county = event.county
                    )
                }

            call.respond(FreeMarkerContent("fires.ftl", mapOf("data" to fires, "region" to region), ""))
        }
    }
}
