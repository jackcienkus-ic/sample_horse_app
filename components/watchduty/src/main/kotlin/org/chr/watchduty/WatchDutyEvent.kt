package org.chr.watchduty

import kotlinx.serialization.Serializable


@Serializable
data class WatchDutyEventData(
    val acreage: Double? = null,  // add = null
)

@Serializable
data class WatchDutyEvent(
    val id: Int,
    val name: String,
    val data: WatchDutyEventData?,
    val lng: Double?,
    val lat: Double?,
    var county: String = "Unknown"
) {
    val acreage: Double? get() = data?.acreage
}

fun WatchDutyEvent.eventAsRow() = """
    <tr>
        <td>$name</td><td>$acreage</td><td>$lat</td><td>$lng</td><td>$county</td>
    </tr>
    """.trimIndent()

fun List<WatchDutyEvent>.fireAsTable() = this.joinToString(
    prefix = "<table rules=\"all\"><tr><th>Name</th><th>Size</th><th>Latitude</th><th>Longitude</th><th>County</tr>",
    postfix = "</table>",
    separator = "\n",
    transform = WatchDutyEvent::eventAsRow
)