package org.chr.watchduty

import java.util.*

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
}
