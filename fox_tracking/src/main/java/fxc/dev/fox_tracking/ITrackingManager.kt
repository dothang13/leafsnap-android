package fxc.dev.fox_tracking

import android.app.Application
import fxc.dev.fox_tracking.adjust.IAdjustTokenConstants
import fxc.dev.fox_tracking.entity.AdRevenueTracking
import fxc.dev.fox_tracking.entity.PurchaseTracking
import fxc.dev.fox_tracking.firebase.IFirebaseConstants
import android.os.Bundle

/**
 * ITrackingManager is a abstract class representing a TrackingManager.
 *
 * This class provides a framework for configuring event tracking for various analytics platforms and logging different types of events.
 */
abstract class ITrackingManager internal constructor() {
    /**
     * A mutable list of EventTracking instances.
     */
    private val eventTrackingList = mutableListOf<EventTracking>()

    /**
     * A Adjust token constants of EventTracking instances.
     */
    private var mAdjustTokenConstants: IAdjustTokenConstants? = null
    /**
     * A Firebase  constants of EventTracking instances.
     */
    private var firebaseTokenConstants: IFirebaseConstants? = null

    /**
     * Configures event tracking for various analytics platforms.
     *
     * @param application The Application instance.
     * @param applicationId The ID of the application.
     * @param adjustTokenConstants The Adjust Token constants.
     * @param appsFlyerId The AppsFlyer ID.
     */
    open fun configure(
        application: Application,
        applicationId: String,
        adjustTokenConstants: IAdjustTokenConstants,
        firebaseConstants: IFirebaseConstants,
        appsFlyerId: String,
        trackingUrl: String
    ) {
        mAdjustTokenConstants = adjustTokenConstants
        firebaseTokenConstants = firebaseConstants
    }

    /**
     * Logs a purchase event by calling the logPurchaseEvent method on each registered EventTracking instance.
     *
     * @param purchaseTracking The PurchaseTracking object containing the purchase event details.
     */
    fun logPurchaseEvent(purchaseTracking: PurchaseTracking) =
        eventTrackingList.forEach { it.logPurchaseEvent(purchaseTracking) }

    /**
     * Logs a ad revenue event by calling the logAdRevenueEvent method on each registered EventTracking instance.
     *
     * @param adRevenueTracking The AdRevenueTracking object containing the ad revenue event details.
     */
    fun logAdRevenueEvent(adRevenueTracking: AdRevenueTracking) =
        eventTrackingList.forEach { it.logAdRevenueEvent(adRevenueTracking) }

    /**
     * Logs a custom event by calling the logCustomEvent method on each registered EventTracking instance.
     *
     * @param event The bundle of the custom event and parameters associated with the event.
     */
    fun logCustomEvent(event: Bundle) =
        eventTrackingList.forEach { it.logCustomEvent(event) }

    /**
     * Adds an EventTracking instance to the list of event tracking instances.
     *
     * @param eventTracking The EventTracking instance to be added.
     * @return The updated TrackingManager instance.
     */
    fun add(eventTracking: EventTracking) = apply { eventTrackingList.add(eventTracking) }

    /**
     * Get the instance of IAdjustTokenConstants of event tracking instances.
     *
     * @return The instance of IAdjustTokenConstants.
     */
    fun getAdjustTokenConstants(): IAdjustTokenConstants = mAdjustTokenConstants
        ?: throw NullPointerException("The instance of IAdjustTokenConstants was null.")

    /**
     * Get the instance of IFirebaseConstants of event tracking instances.
     *
     * @return The instance of IFirebaseConstants.
     */
    fun getFirebaseConstants(): IFirebaseConstants = firebaseTokenConstants
        ?: throw NullPointerException("The instance of IFirebaseConstants was null.")
}
