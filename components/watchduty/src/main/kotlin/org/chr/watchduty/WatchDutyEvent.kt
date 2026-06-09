package org.chr.watchduty

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WatchDutyEventData(
    val acreage: Double? = null,  // add = null
)

@Serializable
data class WatchDutyEvent(
    @SerialName("external_id") val id: String? = null,
    @SerialName("geo_event_type") val type: String? = null,
    val name: String? = null,
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val data: WatchDutyEventData? = null,
    var county: String = "Unknown"
) {
    val acreage: Double? get() = data?.acreage
}
