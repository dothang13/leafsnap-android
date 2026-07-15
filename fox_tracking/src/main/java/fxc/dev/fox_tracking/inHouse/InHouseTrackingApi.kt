package fxc.dev.fox_tracking.inHouse

import fxc.dev.fox_tracking.inHouse.model.InHouseTrackingRequest
import fxc.dev.fox_tracking.inHouse.model.InHouseTrackingResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * InHouseTrackingApi is an interface for the in-house tracking API.
 */
interface InHouseTrackingApi {
    /**
     * Logs tracking of a purchase.
     *
     * @param request The tracking request containing purchase information.
     * @return The tracking response indicating the status of the tracking operation.
     */
    @POST("android-log")
    suspend fun trackingPurchase(@Body request: InHouseTrackingRequest): InHouseTrackingResponse
}
