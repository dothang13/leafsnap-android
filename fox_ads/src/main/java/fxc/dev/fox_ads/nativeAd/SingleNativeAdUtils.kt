package fxc.dev.fox_ads.nativeAd

import android.app.Activity
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.VideoOptions
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import fxc.dev.common.premium.IPremiumManager
import fxc.dev.fox_ads.base.BaseAdUtils
import fxc.dev.fox_ads.extensions.trackAdRevenueOnPaid
import fxc.dev.fox_ads.ump.GoogleMobileAdsConsentManager
import fxc.dev.fox_tracking.AdEventConstants
import fxc.dev.fox_tracking.EventKeyConstants
import fxc.dev.fox_tracking.AdTypeConstants
import fxc.dev.fox_tracking.EventTypeConstants
import fxc.dev.fox_tracking.ITrackingManager
import timber.log.Timber
import androidx.core.os.bundleOf

class SingleNativeAdUtils internal constructor(
    private val premiumManager: IPremiumManager,
    private val trackingManager: ITrackingManager,
    private val googleMobileAdsConsentManager: GoogleMobileAdsConsentManager
) : BaseAdUtils(premiumManager, trackingManager) {
    fun loadAd(
        activity: Activity,
        adId: String,
        onLoadFailed: () -> Unit,
        onAdLoaded: (NativeAd) -> Unit
    ) {
        if (!appAllowShowAd) {
            Timber.d("App not allow to show ad.")
            onLoadFailed()
            return
        }

        if (!googleMobileAdsConsentManager.canRequestAds) {
            Timber.d("Mobile Ads consent manager cannot request ads.")
            onLoadFailed()
            return
        }

        val videoOptions = VideoOptions
            .Builder()
            .setStartMuted(true)
            .build()

        val nativeAdOptions = NativeAdOptions
            .Builder()
            .setVideoOptions(videoOptions)
            .build()

        val adListener = object : AdListener() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                super.onAdFailedToLoad(adError)
                Timber.e("Ad failed to load, domain: ${adError.domain}, code: ${adError.code}, message: ${adError.message}.")
                onLoadFailed()
            }

            override fun onAdClicked() {
                super.onAdClicked()
                val firebaseBundle = bundleOf(
                    firebaseConstants.SCREEN_NAME to activity::class.java.simpleName,
                    firebaseConstants.AD_TYPE to AdTypeConstants.NATIVE,
                    firebaseConstants.UNIT_ID to adId,
                    EventKeyConstants.EVENT_NAME to AdEventConstants.NATIVE_AD_CLICK
                )
                trackingManager.logCustomEvent(
                    bundleOf(
                        EventTypeConstants.ADJUST to adjustTokenConstants.NATIVE_AD_CLICK,
                        EventTypeConstants.FIREBASE to firebaseBundle
                    )
                )
            }

            override fun onAdImpression() {
                super.onAdImpression()
                val firebaseBundle = bundleOf(
                    firebaseConstants.SCREEN_NAME to activity::class.java.simpleName,
                    firebaseConstants.AD_TYPE to AdTypeConstants.NATIVE,
                    firebaseConstants.UNIT_ID to adId,
                    EventKeyConstants.EVENT_NAME to AdEventConstants.NATIVE_AD_IMPRESSION
                )
                trackingManager.logCustomEvent(
                    bundleOf(
                        EventTypeConstants.ADJUST to adjustTokenConstants.NATIVE_AD_IMPRESSION,
                        EventTypeConstants.FIREBASE to firebaseBundle
                    )
                )
            }
        }

        val adLoader = AdLoader
            .Builder(activity, adId)
            .forNativeAd { ad ->
                Timber.d("Loaded nativeAd = $ad.")
                ad.trackAdRevenueOnPaid(trackingManager)

                // If this callback occurs after the activity is destroyed, you must call
                // destroy and return or you may get a memory leak.
                if (activity.isDestroyed || activity.isFinishing || activity.isChangingConfigurations) {
                    ad.destroy()
                    return@forNativeAd
                }

                onAdLoaded(ad)
            }.withAdListener(adListener)
            .withNativeAdOptions(nativeAdOptions)
            .build()

        adLoader.loadAd(defaultAdRequest)
    }
}
