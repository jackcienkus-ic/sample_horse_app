package org.chr.watchduty

import kotlinx.serialization.json.Json


class WatchDutyService() {
    fun latestResources(): List<WatchDutyEvent> {
        val rawFires = WatchDutyJsonToString().readJSONFromResources("latest.json")
            ?: error("Could not read latest.json")

        val json = Json { ignoreUnknownKeys = true }
        val fires = json.decodeFromString<List<WatchDutyEvent>>(rawFires)
        return fires
    }

    fun latestHTTP(): List<WatchDutyEvent> {
        val fireData = HTMLPull.getFires("https://api.watchduty.org/api/v1/geo_events/?geo_event_types=wildfire,location")
        val json = Json { ignoreUnknownKeys = true }
        val fires = json.decodeFromString<List<WatchDutyEvent>>(fireData)
        return fires
        }
}
