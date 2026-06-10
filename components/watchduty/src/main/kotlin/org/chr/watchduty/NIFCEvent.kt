package org.chr.watchduty

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class FireEvent(
    val id: Int? = null,
    val geometry: Geometry? = null,
    val properties: FireProperties? = null,
    @SerialName("name") val wildwebName: String? = null,
    @SerialName("acres") val wildwebAcres: String? = null,
    val latitude: String? = null,
    val longitude: String? = null,
    val date: String? = null,
    @SerialName("type") val wildwebType: String? = null,
    ) {
    val name: String? get() = properties?.incidentName ?: wildwebName
    val lat: Double get() = geometry?.coordinates?.getOrNull(1)
        ?: latitude?.toDoubleOrNull()
        ?: 0.0
    val lng: Double get() = geometry?.coordinates?.getOrNull(0)
        ?: longitude?.toDoubleOrNull()?.let {-it}
        ?: 0.0
    val acreage: Double? get() = properties?.incidentSize
        ?: wildwebAcres?.toDoubleOrNull()
    var county: String = properties?.pooCounty
        ?: "Durango"
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
    @SerialName("FireDiscoveryDateTime") val fireDiscoveryDateTime: Long? = null,
    @SerialName("IncidentTypeCategory") val incidentTypeCategory: String? = null,
    )

@Serializable
data class FeatureCollection(
    val features: List<FireEvent> = emptyList()
)

@Serializable
data class WildWebCollection(
    val data: List<FireEvent> = emptyList()
)