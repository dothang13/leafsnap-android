package fxc.dev.fox_tracking.inHouse

import fxc.dev.fox_tracking.BuildConfig
import fxc.dev.fox_tracking.EventTracking
import fxc.dev.fox_tracking.entity.AdRevenueTracking
import fxc.dev.fox_tracking.entity.PurchaseTracking
import fxc.dev.fox_tracking.inHouse.model.InHouseTrackingRequest
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import android.os.Bundle

/**
 * InHouseEventTracking is a class that implements event tracking using an in-house tracking API.
 *
 * @param applicationId The ID of the application.
 */
class InHouseEventTracking internal constructor(
    private val applicationId: String,
    private val trackingUrl: String
) : EventTracking {
    private val scope =
        CoroutineScope(Dispatchers.IO + SupervisorJob() + CoroutineName("InHouseEventTrackingScope"))
    private val apiService by lazy {
        InHouseRetrofitClient.create(trackingUrl)
    }

    override fun logPurchaseEvent(purchaseTracking: PurchaseTracking) {
        if (!BuildConfig.DEBUG) {
            val exceptionHandler = CoroutineExceptionHandler { _, error ->
                Timber.e("Log purchase event failed with error: $error.")
            }

            scope.launch(exceptionHandler) {
                val request = InHouseTrackingRequest(applicationId, purchaseTracking.periods.value)
                val response = apiService.trackingPurchase(request)
                Timber.d("Log purchase event success: $response.")
            }
        } else {
            Timber.d("Skip log purchase event when debug.")
        }
    }

    override fun logAdRevenueEvent(adRevenueTracking: AdRevenueTracking) {
    }

    override fun logCustomEvent(event: Bundle) {
    }
}
