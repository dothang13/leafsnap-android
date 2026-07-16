package fxc.dev.fox_tracking.firebase

import android.os.Bundle
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import fxc.dev.common.Fox
import fxc.dev.fox_tracking.EventTracking
import fxc.dev.fox_tracking.entity.AdRevenueTracking
import fxc.dev.fox_tracking.entity.PurchaseTracking
import fxc.dev.fox_tracking.EventKeyConstants
import fxc.dev.fox_tracking.EventTypeConstants
import fxc.dev.fox_tracking.tracking

/**
 * FirebaseEventTracking is a class that implements event tracking using the Firebase Analytics SDK.
 */
class FirebaseEventTracking internal constructor(
    private val firebaseConstants: IFirebaseConstants
) : EventTracking {
    override fun logPurchaseEvent(purchaseTracking: PurchaseTracking) {
        Firebase.analytics.logEvent(FirebaseAnalytics.Event.PURCHASE) {
            param(firebaseConstants.PRODUCT_ID, purchaseTracking.productId)
            param(FirebaseAnalytics.Param.ITEM_NAME, purchaseTracking.periods.value)
            param(FirebaseAnalytics.Param.CONTENT_TYPE, purchaseTracking.contentType)
            param(FirebaseAnalytics.Param.PRICE, purchaseTracking.price / 1000000.0)
            param(FirebaseAnalytics.Param.CURRENCY, purchaseTracking.currencyCode)
            param(firebaseConstants.SCREEN_SHOW_INAPP, Fox.tracking.lastScreenBeforeSubscription.toString())
        }
    }

    override fun logAdRevenueEvent(adRevenueTracking: AdRevenueTracking) {
        Firebase.analytics.logEvent(firebaseConstants.AD_REVENUE) {
            param(FirebaseAnalytics.Param.VALUE, adRevenueTracking.revenue)
            param(FirebaseAnalytics.Param.CURRENCY, adRevenueTracking.currencyCode)
            param(firebaseConstants.AD_NETWORK, adRevenueTracking.network)
        }
    }

    override fun logCustomEvent(event: Bundle) {
        val firebaseBundle = event.getBundle(EventTypeConstants.FIREBASE)
        firebaseBundle?.getString(EventKeyConstants.EVENT_NAME)?.let { firebaseEventName ->
            Firebase.analytics.logEvent(firebaseEventName, firebaseBundle.apply { remove(EventKeyConstants.EVENT_NAME) })
        }
    }
}
