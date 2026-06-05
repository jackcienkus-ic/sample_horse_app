package org.chr.watchduty

import java.util.*

class Application() {

    // todo -
    // introduce ktor
    // introduce the database

    fun extracted(): List<WatchDutyEvent> {
        val service = WatchDutyService()
        val latest = service.latest()
        println("Fetched latest watch duty data")
        return latest
    }
}

fun main() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    Application().extracted()
}
