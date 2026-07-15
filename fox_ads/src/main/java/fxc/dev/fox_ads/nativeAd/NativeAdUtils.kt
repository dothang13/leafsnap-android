package fxc.dev.fox_ads.nativeAd

import android.content.Context
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import timber.log.Timber
import androidx.core.os.bundleOf
import fxc.dev.common.Fox
import fxc.dev.fox_tracking.tracking

class NativeAdUtils internal constructor(
    private val nativeAdId: String,
    private val numberOfAdsToLoad: Int,
    private val context: Context,
    private val premiumManager: IPremiumManager,
    private val trackingManager: ITrackingManager,
    private val googleMobileAdsConsentManager: GoogleMobileAdsConsentManager
) : BaseAdUtils(premiumManager, trackingManager) {
    private val adLoader: AdLoader
    private val nativeAdMutableList = mutableListOf<NativeAd>()
    private val _nativeAdsFlow = MutableStateFlow<List<NativeAd>>(emptyList())
    val nativeAdsFlow = _nativeAdsFlow
        .combine(premiumManager.getSubscribedStateFlow()) { ads, purchased -> if (purchased) emptyList() else ads }

    init {
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
            }

            override fun onAdClicked() {
                super.onAdClicked()
                val firebaseBundle = bundleOf(
                    firebaseConstants.SCREEN_NAME to Fox.tracking.lastScreenBeforeSubscription,
                    firebaseConstants.AD_TYPE to AdTypeConstants.NATIVE,
                    firebaseConstants.UNIT_ID to nativeAdId,
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
                    firebaseConstants.SCREEN_NAME to Fox.tracking.lastScreenBeforeSubscription,
                    firebaseConstants.AD_TYPE to AdTypeConstants.NATIVE,
                    firebaseConstants.UNIT_ID to nativeAdId,
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

        adLoader = AdLoader
            .Builder(context, nativeAdId)
            .forNativeAd { ad ->
                Timber.d("Loaded nativeAd = $ad.")
                ad.trackAdRevenueOnPaid(trackingManager)
                nativeAdMutableList.add(ad)
                _nativeAdsFlow.tryEmit(nativeAdMutableList)
            }.withAdListener(adListener)
            .withNativeAdOptions(nativeAdOptions)
            .build()
    }

    internal fun loadAds() {
        if (!appAllowShowAd) {
            Timber.d("App not allow to show ad.")
            return
        }

        if (!googleMobileAdsConsentManager.canRequestAds) {
            Timber.d("Mobile Ads consent manager cannot request ads.")
            return
        }

        adLoader.loadAds(
            defaultAdRequest,
            numberOfAdsToLoad
        )
    }
}
