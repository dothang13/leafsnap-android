package fxc.dev.fox_tracking.appsflyer

import android.content.Context
import android.os.Bundle
import com.appsflyer.AFInAppEventParameterName
import com.appsflyer.AFInAppEventType
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import fxc.dev.fox_tracking.BuildConfig
import fxc.dev.fox_tracking.EventTracking
import fxc.dev.fox_tracking.entity.AdRevenueTracking
import fxc.dev.fox_tracking.entity.PurchaseTracking
import timber.log.Timber

/**
 * AppsFlyerEventTracking is a class that implements event tracking using the AppsFlyer SDK.
 *
 * @param context The application context.
 * @param appsFlyerId The AppsFlyer ID for your application.
 */
class AppsFlyerEventTracking internal constructor(
    private val context: Context,
    appsFlyerId: String
) : EventTracking {
    private val appsFlyer = AppsFlyerLib.getInstance()

    init {
        appsFlyer.run {
            setDebugLog(BuildConfig.DEBUG)
            init(appsFlyerId, null, context)
            start(context)
        }
    }

    override fun logPurchaseEvent(purchaseTracking: PurchaseTracking) {
        appsFlyer.logEvent(
            context,
            AFInAppEventType.PURCHASE,
            mapOf(
                AFInAppEventParameterName.PRICE to purchaseTracking.price,
                AFInAppEventParameterName.CONTENT_TYPE to purchaseTracking.contentType,
                AFInAppEventParameterName.CONTENT_ID to purchaseTracking.productId,
                AFInAppEventParameterName.REVENUE to purchaseTracking.price / 1000000.0,
                AFInAppEventParameterName.CURRENCY to purchaseTracking.currencyCode
            ),
            object : AppsFlyerRequestListener {
                override fun onSuccess() {
                    Timber.d("Log purchase event success.")
                }

                override fun onError(p0: Int, p1: String) {
                    Timber.e("Log purchase event failed with error: $p1.")
                }
            }
        )
    }

    override fun logAdRevenueEvent(adRevenueTracking: AdRevenueTracking) {
    }

    override fun logCustomEvent(event: Bundle) {
    }
}
