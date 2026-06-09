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