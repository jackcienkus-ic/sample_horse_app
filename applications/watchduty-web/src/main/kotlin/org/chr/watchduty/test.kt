package org.chr.watchduty

import kotlin.math.cos

fun main(){
    println("test")
    val mileDiff=10
    val events = WatchDutyService("https://api.watchduty.org").latest()
        .filter { event ->
            val lat = event.lat
            val lng = event.lng
            val latExpansion = mileDiff / 69.1
            val lngExpansion = mileDiff / (69.17 * cos(Math.toRadians(lat)))
            lat in (36.99 - latExpansion)..(41.00 + latExpansion) &&
                    lng in (-109.05 - lngExpansion)..(-102.05 + lngExpansion)
        }


    println(events)
    println(events.size)
}