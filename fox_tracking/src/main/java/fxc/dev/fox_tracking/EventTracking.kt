package fxc.dev.fox_tracking

import fxc.dev.fox_tracking.entity.AdRevenueTracking
import fxc.dev.fox_tracking.entity.PurchaseTracking
import android.os.Bundle

/**
 * EventTracking is an interface that defines methods for logging purchase events and custom events.
 */
interface EventTracking {
    /**
     * Logs a purchase event.
     *
     * @param purchaseTracking The PurchaseTracking object containing the purchase event details.
     */
    fun logPurchaseEvent(purchaseTracking: PurchaseTracking)

    /**
     * Logs a ad revenue event.
     *
     * @param adRevenueTracking The AdRevenueTracking object containing the ad revenue event details.
     */
    fun logAdRevenueEvent(adRevenueTracking: AdRevenueTracking)

    /**
     * Logs an custom event.
     *
     * @param event An bundle with the custom event details.
     */
    fun logCustomEvent(event: Bundle)
}
