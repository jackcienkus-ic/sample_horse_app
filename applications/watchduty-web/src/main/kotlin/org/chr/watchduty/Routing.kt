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

            println("1")
            val NIFCevents = service.latest("https://services3.arcgis.com/T4QMspbfLg3qTGWY/arcgis/rest/services/WFIGS_Incident_Locations_Current/FeatureServer/0/query?outFields=*&where=1%3D1&f=geojson", "NIFC")
            println("2")
            val WildWebEventsDurango = service.latest("https://snknmqmon6.execute-api.us-west-2.amazonaws.com/centers/CODRC/incidents", "Wild Web Durango")
            val WildWebEventsMontrose = service.latest("https://snknmqmon6.execute-api.us-west-2.amazonaws.com/centers/COMTC/incidents", "Wild Web Montrose")
            val fires = (NIFCevents+WildWebEventsDurango+WildWebEventsMontrose)
                .filter { event ->
                    val lat = event.lat
                    val lng = event.lng
                    val latExpansion = mileDiff / 69.1
                    val lngExpansion = mileDiff / (69.17 * cos(Math.toRadians(lat)))
                    val oneWeekAgo = System.currentTimeMillis() - 7L * 24 * 60 * 60 * 1000
                    val discoveryTime = event.properties?.fireDiscoveryDateTime ?: event.date?.let {
                        java.time.Instant.parse(it.replace(" ", "T") + "Z").toEpochMilli()
                    }
                    val isWildfire = event.properties?.incidentTypeCategory == "WF"
                            || event.wildwebType == "Wildfire"
                    lat in (36.99 - latExpansion)..(41.00 + latExpansion) &&
                            lng in (-109.05 - lngExpansion)..(-102.05 + lngExpansion) &&
                            (discoveryTime == null || discoveryTime >= oneWeekAgo) &&
                            isWildfire
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
