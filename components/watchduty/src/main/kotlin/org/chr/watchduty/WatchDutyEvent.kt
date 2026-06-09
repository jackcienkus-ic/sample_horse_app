package org.chr.watchduty

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class WatchDutyEvent(
    val id: Int? = null,
    val geometry: Geometry? = null,
    val properties: FireProperties? = null
) {
    val name: String? get() = properties?.incidentName
    val lat: Double get() = geometry?.coordinates?.getOrNull(1) ?: 0.0
    val lng: Double get() = geometry?.coordinates?.getOrNull(0) ?: 0.0
    val acreage: Double? get() = properties?.incidentSize
    var county: String = properties?.pooCounty ?: "Unknown"
}

@Serializable
data class Geometry(
    val coordinates: List<Double> = emptyList()
)

@Serializable
data class FireProperties(
    @SerialName("IncidentName") val incidentName: String? = null,
    @SerialName("IncidentSize") val incidentSize: Double? = null,
    @SerialName("POOCounty") val pooCounty: String? = null,
)

@Serializable
data class FeatureCollection(
    val features: List<WatchDutyEvent> = emptyList()
)