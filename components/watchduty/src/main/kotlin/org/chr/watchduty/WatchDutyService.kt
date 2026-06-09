package org.chr.watchduty

import kotlinx.serialization.json.Json
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers

class WatchDutyService(
    val baseUrl: String,
    val client: HttpClient = HttpClient.newHttpClient()
) {
    private val json = Json { ignoreUnknownKeys = true }

    fun latest(): List<WatchDutyEvent> {
        val uri = URI.create("https://services3.arcgis.com/T4QMspbfLg3qTGWY/arcgis/rest/services/WFIGS_Incident_Locations_Current/FeatureServer/0/query?outFields=*&where=1%3D1&f=geojson")
        val request = HttpRequest.newBuilder()
            .uri(uri)
            .build()

        val response = client.send(request, BodyHandlers.ofString())
        val body = response.body()

        if (response.statusCode() != 200) {
            throw RuntimeException("Failed to fetch latest watch duty events: ${response.statusCode()} - $body")
        }

        return json.decodeFromString<FeatureCollection>(body).features
    }
}