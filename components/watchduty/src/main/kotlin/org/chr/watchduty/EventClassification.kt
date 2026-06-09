package org.chr.watchduty

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlin.math.cos

class EventClassification(){

    fun assignCounty(fire: WatchDutyEvent): String{
        val countyUrl = "https://geocoding.geo.census.gov/geocoder/geographies/coordinates?x=${fire.lng}&y=${fire.lat}&benchmark=Public_AR_Current&vintage=Current_Current&layers=Counties&format=json"
        val client = HttpClient.newHttpClient()
        val countyRequest = HttpRequest.newBuilder()
            .uri(URI.create(countyUrl))
            .GET()
            .build()
        val countyResponse = client.send(countyRequest, HttpResponse.BodyHandlers.ofString())
        val countyBody = countyResponse.body() ?: return "unknown"
        val countyRoot = Json.parseToJsonElement(countyBody).jsonObject
        val counties = countyRoot["result"]
            ?.jsonObject?.get("geographies")
            ?.jsonObject?.get("Counties")
            ?.jsonArray
        return counties?.firstOrNull()
            ?.jsonObject?.get("NAME")
            ?.jsonPrimitive?.contentOrNull ?: "Unknown:"
    }
}