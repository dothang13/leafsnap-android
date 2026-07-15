package fxc.dev.fox_tracking.inHouse.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * InHouseTrackingResponse is a data class representing a response from the in-house tracking API.
 *
 * @property id The ID of the tracking response.
 * @property appID The ID of the application.
 * @property purchase The purchase information.
 * @property ip The IP address associated with the tracking response.
 * @property countryOrRegion The country or region associated with the tracking response.
 * @property createdAt The timestamp indicating when the tracking response was created.
 * @property updatedAt The timestamp indicating when the tracking response was last updated.
 */
@Serializable
data class InHouseTrackingResponse(
    @SerialName("_id")
    val id: String,
    @SerialName("appID")
    val appID: String,
    @SerialName("purchase")
    val purchase: String,
    @SerialName("ip")
    val ip: String,
    @SerialName("countryOrRegion")
    val countryOrRegion: String,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("updatedAt")
    val updatedAt: String
)
