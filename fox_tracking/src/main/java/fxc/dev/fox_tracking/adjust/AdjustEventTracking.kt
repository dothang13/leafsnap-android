package fxc.dev.fox_tracking.adjust

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustAdRevenue
import com.adjust.sdk.AdjustConfig
import com.adjust.sdk.AdjustEvent
import com.adjust.sdk.AdjustPlayStoreSubscription
import com.adjust.sdk.LogLevel
import fxc.dev.fox_tracking.BuildConfig
import fxc.dev.fox_tracking.EventTracking
import fxc.dev.fox_tracking.entity.AdRevenueTracking
import fxc.dev.fox_tracking.entity.PurchaseTracking
import fxc.dev.fox_tracking.EventKeyConstants
import fxc.dev.fox_tracking.EventTypeConstants

/**
 * AdjustEventTracking is a class that implements event tracking using the Adjust SDK.
 *
 * @param application The application instance.
 * @param adjustTokenConstants The Adjust Token constants.
 */
class AdjustEventTracking internal constructor(
    application: Application,
    private val adjustTokenConstants: IAdjustTokenConstants
) : EventTracking {
    init {
        val config = AdjustConfig(
            application,
            adjustTokenConstants.ADJUST_TOKEN,
            if (BuildConfig.DEBUG) AdjustConfig.ENVIRONMENT_SANDBOX else AdjustConfig.ENVIRONMENT_PRODUCTION
        )
        config.setLogLevel(LogLevel.VERBOSE)
        Adjust.initSdk(config)

        registerAdjustTrackingCallback(application)
    }

    /**
     * Registers the Adjust tracking callback to handle activity lifecycle events.
     *
     * @param application The application instance.
     */
    private fun registerAdjustTrackingCallback(application: Application) {
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
            }

            override fun onActivityStarted(p0: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
                Adjust.onResume()
            }

            override fun onActivityPaused(activity: Activity) {
                Adjust.onPause()
            }

            override fun onActivityStopped(p0: Activity) {
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
            }

            override fun onActivityDestroyed(p0: Activity) {
            }
        })
    }

    override fun logPurchaseEvent(purchaseTracking: PurchaseTracking) {
        // Tracking purchase key event
        val adjustEvent = AdjustEvent(adjustTokenConstants.PURCHASE_KEY)
        adjustEvent.setRevenue(
            purchaseTracking.price / 1000000.0,
            purchaseTracking.currencyCode
        )
        adjustEvent.orderId = purchaseTracking.productId
        Adjust.trackEvent(adjustEvent)

        // Track play store subscription event
        val subscription = AdjustPlayStoreSubscription(
            purchaseTracking.price / 1000000,
            purchaseTracking.currencyCode,
            purchaseTracking.productId,
            purchaseTracking.orderId,
            purchaseTracking.signature,
            purchaseTracking.purchaseToken
        )
        subscription.purchaseTime = purchaseTracking.purchaseTime
        Adjust.trackPlayStoreSubscription(subscription)

        // Track custom event
        Adjust.trackEvent(AdjustEvent(purchaseTracking.adjustTrackingId))
    }

    override fun logAdRevenueEvent(adRevenueTracking: AdRevenueTracking) {
        val adRevenue = AdjustAdRevenue("admob_sdk")
        adRevenue.setRevenue(adRevenueTracking.revenue, adRevenueTracking.currencyCode)
        adRevenue.adRevenueNetwork = adRevenueTracking.network
        Adjust.trackAdRevenue(adRevenue)

        val adjust = AdjustEvent(adjustTokenConstants.AD_REVENUE)
        adjust.setRevenue(adRevenueTracking.revenue, adRevenueTracking.currencyCode)
        Adjust.trackEvent(adjust)
    }

    override fun logCustomEvent(event: Bundle) {
        event.getString(EventTypeConstants.ADJUST)?.let { adjustToken ->
            Adjust.trackEvent(AdjustEvent(adjustToken))
        }
    }
}
