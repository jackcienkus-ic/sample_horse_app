package org.chr.watchduty

import kotlinx.serialization.Serializable

@Serializable
data class WatchDutyEventData(
    val acreage: Double? = null,
)
