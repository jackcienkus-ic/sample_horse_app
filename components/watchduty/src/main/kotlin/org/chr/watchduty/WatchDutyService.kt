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

    fun latestResources(): List<WatchDutyEvent> {
        val rawFires = WatchDutyJsonToString().readJSONFromResources("latest.json")
            ?: error("Could not read latest.json")

        val json = Json { ignoreUnknownKeys = true }
        val fires = json.decodeFromString<List<WatchDutyEvent>>(rawFires)
        return fires
    }

    fun latestHTTP(): List<WatchDutyEvent> {
        val uri = URI.create(baseUrl).resolve("/api/v1/geo_events/?geo_event_types=wildfire,location")
        val request = HttpRequest.newBuilder()
            .uri(uri)
            .headers("Accept", "application/json")
            .headers("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/148.0.0.0 Safari/537.36")
            .header("Accept-Language", "fr-CA,en;q=0.9")
            .build()

        val response = client.send(request, BodyHandlers.ofString())
        val body = response.body()

        return json.decodeFromString<List<WatchDutyEvent>>(body)
    }
}
