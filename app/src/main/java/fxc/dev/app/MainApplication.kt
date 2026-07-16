package fxc.dev.app

import android.app.Application
import androidx.core.os.bundleOf
import com.chibatching.remotekonfig.RemoteKonfig
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import dagger.hilt.android.HiltAndroidApp
import fxc.dev.app.ui.splash.SplashActivity
import fxc.dev.app.utils.AdjustTokenConstants
import fxc.dev.app.utils.AdsConstants
import fxc.dev.app.utils.AppKonfig
import fxc.dev.app.utils.AppPref
import fxc.dev.app.utils.AppUtils
import fxc.dev.app.utils.FirebaseConstants
import fxc.dev.common.Fox
import fxc.dev.fox_ads.ads
import fxc.dev.fox_billing.billing
import fxc.dev.fox_billing.model.IAPProduct
import fxc.dev.fox_billing.model.IAPProductType
import fxc.dev.fox_tracking.EventTypeConstants
import fxc.dev.fox_tracking.tracking
import timber.log.Timber
import kotlin.time.Duration.Companion.milliseconds

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this

        configureFrameworks()
        trackingFirstOpenApp()
        initFcm()

        RemoteKonfig
            .initialize {
                minimumFetchIntervalInSeconds = if (BuildConfig.DEBUG) 0L else 3600L
                registerModels(AppKonfig)
            }.addOnSuccessListener {
                Fox.ads.updateTimeIntervalShowInterstitialAd(AppKonfig.delayShowInternalInterstitial.milliseconds)
            }
    }

    private fun configureFrameworks() {
        Fox.tracking.configure(
            application = this,
            applicationId = BuildConfig.APPLICATION_ID,
            adjustTokenConstants = AdjustTokenConstants,
            firebaseConstants = FirebaseConstants,
            appsFlyerId = resources.getString(R.string.app_flyer_id),
            trackingUrl = resources.getString(R.string.tracking_domain_url)
        )

        Fox.billing.configure(
            iapProducts = listOf(
                IAPProduct(
                    productType = IAPProductType.Subscription,
                    productId = resources.getString(R.string.billing_sub_week),
                    adjustTrackingId = AdjustTokenConstants.WEEKLY_PURCHASED_KEY
                ),
                IAPProduct(
                    productType = IAPProductType.Subscription,
                    productId = resources.getString(R.string.billing_sub_month),
                    adjustTrackingId = AdjustTokenConstants.MONTHLY_PURCHASED_KEY
                ),
                IAPProduct(
                    productType = IAPProductType.Subscription,
                    productId = resources.getString(R.string.billing_sub_year),
                    adjustTrackingId = AdjustTokenConstants.YEARLY_PURCHASED_KEY
                ),
                IAPProduct(
                    productType = IAPProductType.InApp,
                    productId = resources.getString(R.string.billing_inapp_onetime),
                    adjustTrackingId = AdjustTokenConstants.ONETIME_PURCHASED_KEY
                )
            )
        )

        Fox.ads.configure(
            adsConstants = AdsConstants,
            disableAppOpenAdActivities = listOf(
                SplashActivity::class.java
            )
        )
    }

    private fun initFcm() {
        val countryCode = AppUtils.getCountryCodeFromNetwork(this)
        val topic =
            "AppID" + if (countryCode.isNotEmpty()) "-$countryCode" else "" // Ví dụ: A123-vn hoặc A123 nếu countryCode is empty
        Timber.d("$TAG Subscribing to topic: $topic")
        Firebase.messaging.subscribeToTopic(topic)
            .addOnCompleteListener { task ->
                var msg = "Subscribed"
                if (!task.isSuccessful) {
                    msg = "Subscribe failed"
                }
                Timber.d("$TAG $msg")
            }
    }

    private fun trackingFirstOpenApp() {
        if (!AppPref.hadTrackFirstTimeOpen) {
            Fox.tracking.logCustomEvent(
                bundleOf(EventTypeConstants.ADJUST to AdjustTokenConstants.FIRST_OPEN_ADJUST_KEY)
            )
            AppPref.hadTrackFirstTimeOpen = true
        }
    }

    companion object {
        lateinit var instance: MainApplication
            private set
        private const val TAG = "MainApplication"
    }
}
