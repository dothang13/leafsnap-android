package fxc.dev.fox_ads.extensions

import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.AdapterResponseInfo
import com.google.android.gms.ads.OnPaidEventListener
import com.google.android.gms.ads.ResponseInfo
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.rewarded.RewardedAd
import fxc.dev.fox_tracking.ITrackingManager
import fxc.dev.fox_tracking.entity.AdRevenueTracking
import timber.log.Timber

private fun trackAdRevenueOnPaid(
    setOnPaidEventListener: (OnPaidEventListener?) -> Unit,
    responseInfo: ResponseInfo?,
    trackingManager: ITrackingManager
) {
    setOnPaidEventListener.invoke { adValue ->
        val loadedAdapterResponseInfo: AdapterResponseInfo? =
            responseInfo?.loadedAdapterResponseInfo
        loadedAdapterResponseInfo?.let {
            val adRevenueTracking = AdRevenueTracking(
                adValue.valueMicros / 1000000.0,
                adValue.currencyCode,
                it.adSourceName
            )
            trackingManager.logAdRevenueEvent(adRevenueTracking)
            Timber.d("Tracking ad Revenue: $adRevenueTracking")
        }
    }
}

fun AdView.trackAdRevenueOnPaid(trackingManager: ITrackingManager) {
    trackAdRevenueOnPaid(this::setOnPaidEventListener, responseInfo, trackingManager)
}

fun NativeAd.trackAdRevenueOnPaid(trackingManager: ITrackingManager) {
    trackAdRevenueOnPaid(this::setOnPaidEventListener, responseInfo, trackingManager)
}

fun InterstitialAd.trackAdRevenueOnPaid(trackingManager: ITrackingManager) {
    trackAdRevenueOnPaid(this::setOnPaidEventListener, responseInfo, trackingManager)
}

fun RewardedAd.trackAdRevenueOnPaid(trackingManager: ITrackingManager) {
    trackAdRevenueOnPaid(this::setOnPaidEventListener, responseInfo, trackingManager)
}

fun AppOpenAd.trackAdRevenueOnPaid(trackingManager: ITrackingManager) {
    trackAdRevenueOnPaid(this::setOnPaidEventListener, responseInfo, trackingManager)
}
