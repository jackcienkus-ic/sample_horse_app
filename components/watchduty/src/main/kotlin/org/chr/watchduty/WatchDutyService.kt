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
        val uri = URI.create(baseUrl).resolve("/api/v1/geo_events/?geo_event_types=wildfire,location")
        val request = HttpRequest.newBuilder()
            .uri(uri)
            .header("Accept", "application/json")
            .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/148.0.0.0 Safari/537.36")
            .header("Accept-Language", "en-US,en;q=0.9")
            .build()

        val response = client.send(request, BodyHandlers.ofString())
        val body = response.body()

        if (response.statusCode() != 200) {
            throw RuntimeException("Failed to fetch latest watch duty events: ${response.statusCode()} - $body")
        }

        return json.decodeFromString<List<WatchDutyEvent>>(body)
    }
}
