package fxc.dev.fox_tracking.inHouse.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * InHouseTrackingRequest is a data class representing a tracking request for the in-house tracking API.
 *
 * @property appID The ID of the application.
 * @property purchase The purchase information. This property should have one of the following values: [weekly, monthly, yearly, one-time].
 */
@Serializable
data class InHouseTrackingRequest(
    @SerialName("appID")
    val appID: String,
    @SerialName("purchase")
    val purchase: String
)
