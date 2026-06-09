package org.chr.watchduty

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WatchDutyEvent(
    @SerialName("external_id") val id: String? = null,
    @SerialName("geo_event_type") val type: String? = null,
    val name: String? = null,
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val data: WatchDutyEventData? = null,
)
