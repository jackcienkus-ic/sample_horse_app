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

    fun latest(url: String, source: String): List<FireEvent> {
        val uri = URI.create(url)
        val request = HttpRequest.newBuilder()
            .uri(uri)
            .build()

        val response = client.send(request, BodyHandlers.ofString())
        val body = response.body()

        if (response.statusCode() != 200) {
            throw RuntimeException("Failed to fetch latest watch duty events: ${response.statusCode()} - $body")
        }
        if (source == "NIFC") {
            return json.decodeFromString<FeatureCollection>(body).features
        } else if (source == "Wild Web Durango") {
            return json.decodeFromString<List<WildWebCollection>>(body).flatMap { it.data }
                .onEach { it.county = "La Plata" }
        } else if (source == "Wild Web Montrose") {
            return json.decodeFromString<List<WildWebCollection>>(body).flatMap { it.data }
                .onEach { it.county = "Montrose" }
        } else {
            return json.decodeFromString<FeatureCollection>(body).features
        }
    }
}