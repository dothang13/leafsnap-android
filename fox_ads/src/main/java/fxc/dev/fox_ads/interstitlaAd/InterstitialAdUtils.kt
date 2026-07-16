package fxc.dev.fox_ads.interstitlaAd

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import fxc.dev.common.premium.IPremiumManager
import fxc.dev.fox_ads.AdsManager
import fxc.dev.fox_ads.PrepareLoadingAdsDialog
import fxc.dev.fox_ads.base.BaseAdUtils
import fxc.dev.fox_ads.extensions.trackAdRevenueOnPaid
import fxc.dev.fox_ads.ump.GoogleMobileAdsConsentManager
import fxc.dev.fox_tracking.EventKeyConstants
import fxc.dev.fox_tracking.AdTypeConstants
import fxc.dev.fox_tracking.AdEventConstants
import fxc.dev.fox_tracking.EventTypeConstants
import fxc.dev.fox_tracking.ITrackingManager
import timber.log.Timber
import androidx.core.os.bundleOf
import fxc.dev.fox_ads.extensions.hideStatusBar
import fxc.dev.fox_ads.extensions.restoreStatusBar

class InterstitialAdUtils internal constructor(
    private val interstitialAdId: String,
    private val context: Context,
    private val adsManager: AdsManager,
    private val premiumManager: IPremiumManager,
    private val trackingManager: ITrackingManager,
    private val googleMobileAdsConsentManager: GoogleMobileAdsConsentManager
) : BaseAdUtils(premiumManager, trackingManager) {
    private var mInterstitialAd: InterstitialAd? = null
    private var mAdIsLoading: Boolean = false
    private var isStartShowingAd = false
    private var prepareLoadingAdsDialog: PrepareLoadingAdsDialog? = null

    internal fun loadAd() {
        if (!appAllowShowAd) {
            Timber.d("App not allow to show ad.")
            return
        }

        if (!googleMobileAdsConsentManager.canRequestAds) {
            Timber.d("Mobile Ads consent manager cannot request ads.")
            return
        }

        InterstitialAd.load(
            context,
            interstitialAdId,
            defaultAdRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Timber.e("Ad failed to load, domain: ${adError.domain}, code: ${adError.code}, message: ${adError.message}.")
                    mInterstitialAd = null
                    mAdIsLoading = false
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Timber.d("Ad was loaded.")
                    mInterstitialAd = interstitialAd
                    mInterstitialAd?.trackAdRevenueOnPaid(trackingManager)
                    mAdIsLoading = true
                }
            }
        )
    }

    fun showAd(
        activity: AppCompatActivity,
        onAdsShowed: (() -> Unit)? = null,
        onAdsClosed: (message: String) -> Unit
    ) {
        /**
         * Trong 1 vài trường hợp, hàm showInterstitial được gọi liên tục khi chưa trả về callback
         * (vd: nhấp liên tục vào button để show ad)
         * trên các device yếu thì ad sẽ bị show chậm hơn dẫn đến callback trong fullScreenContentCallback chưa được gọi
         * sử dụng biến isStartShowingAd để tránh trường hợp này, khi hàm showInterstitial được gọi nếu vẫn đang thục hiện việc show ad thì bỏ qua, ko trả về callback
         */
        if (isStartShowingAd) return

        if (!appAllowShowAd) {
            Timber.d("App not allow to show ad.")
            onAdsClosed("App not allow to show ad.")
            return
        }

        if (mInterstitialAd != null) {
            /**
             * Kiểm tra xem có được phép show interstitial ad sau khi đã show app open ad hay không
             * Mặc định sẽ là 60 giây sau khi show app open ad thì sẽ được show interstitial ad
             */
            if (System.currentTimeMillis() - adsManager.getLastTimeShowOpenAd() < adsManager.getTimeIntervalShowFullAd().inWholeMilliseconds) {
                Timber.d("Skip show ad with time interval show full ad.")
                onAdsClosed.invoke("Skip show ad with time interval show full ad.")
                return
            }

            /**
             * Kiểm tra xem có được phép show interstitial ad giữa các lần với nhau hay không
             * Mặc định sẽ là 30 giây sau khi show interstitial ad thì sẽ được show interstitial ad tiếp theo
             */
            if (System.currentTimeMillis() - adsManager.getLastTimeShowInterstitialAd() < adsManager.getTimeIntervalShowInterstitialAd().inWholeMilliseconds) {
                Timber.d("Skip show ad with time interval show interstitial ad.")
                onAdsClosed.invoke("Skip show ad with time interval show full ad.")
                return
            }

            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Timber.d("Ad dismissed full screen content.")
                    mInterstitialAd = null
                    isStartShowingAd = false
                    adsManager.updateIsShowingFullScreenAd(false)
                    adsManager.updateLastTimeShowInterstitialAd(System.currentTimeMillis())
                    activity.restoreStatusBar()
                    onAdsClosed("Ad dismissed full screen content.")
                    loadAd()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Timber.e("Ad failed to show full screen content: ${adError.message}")
                    mInterstitialAd = null
                    isStartShowingAd = false
                    adsManager.updateIsShowingFullScreenAd(false)
                    activity.restoreStatusBar()
                    onAdsClosed("Ad failed to show full screen content: ${adError.message}")
                    loadAd()
                }

                override fun onAdShowedFullScreenContent() {
                    Timber.d("Ad showed fullscreen content.")
                    isStartShowingAd = false
                    activity.hideStatusBar()
                    onAdsShowed?.invoke()
                }

                override fun onAdClicked() {
                    super.onAdClicked()
                    val firebaseBundle = bundleOf(
                        firebaseConstants.SCREEN_NAME to activity::class.java.simpleName,
                        firebaseConstants.AD_TYPE to AdTypeConstants.INTERSTITIAL,
                        firebaseConstants.UNIT_ID to mInterstitialAd?.adUnitId,
                        EventKeyConstants.EVENT_NAME to AdEventConstants.INTER_AD_CLICK
                    )
                    trackingManager.logCustomEvent(
                        bundleOf(
                            EventTypeConstants.ADJUST to adjustTokenConstants.INTER_AD_CLICK,
                            EventTypeConstants.FIREBASE to firebaseBundle
                        )
                    )
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                    val firebaseBundle = bundleOf(
                        firebaseConstants.SCREEN_NAME to activity::class.java.simpleName,
                        firebaseConstants.AD_TYPE to AdTypeConstants.INTERSTITIAL,
                        firebaseConstants.UNIT_ID to mInterstitialAd?.adUnitId,
                        EventKeyConstants.EVENT_NAME to AdEventConstants.INTER_AD_IMPRESSION
                    )
                    trackingManager.logCustomEvent(
                        bundleOf(
                            EventTypeConstants.ADJUST to adjustTokenConstants.INTER_AD_IMPRESSION,
                            EventTypeConstants.FIREBASE to firebaseBundle
                        )
                    )
                }
            }

            if (ProcessLifecycleOwner
                    .get()
                    .lifecycle.currentState
                    .isAtLeast(Lifecycle.State.RESUMED)
            ) {
                adsManager.updateIsShowingFullScreenAd(true)

                try {
                    if (prepareLoadingAdsDialog != null && prepareLoadingAdsDialog!!.isShowing) prepareLoadingAdsDialog!!.dismiss()
                    prepareLoadingAdsDialog = PrepareLoadingAdsDialog(activity)
                    prepareLoadingAdsDialog?.show()
                } catch (e: Exception) {
                    prepareLoadingAdsDialog = null
                    e.printStackTrace()
                }

                Handler(Looper.getMainLooper()).postDelayed({
                    if (activity.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                        Handler(Looper.getMainLooper()).postDelayed(
                            {
                                if (prepareLoadingAdsDialog != null && prepareLoadingAdsDialog!!.isShowing && !activity.isDestroyed) prepareLoadingAdsDialog!!.dismiss()
                            },
                            1000
                        )
                        isStartShowingAd = true
                        if (mInterstitialAd != null) {
                            mInterstitialAd!!.show(activity)
                        } else {
                            adsManager.updateIsShowingFullScreenAd(false)
                            onAdsClosed("Interstitial ad wasn't ready yet.")
                        }
                    } else {
                        if (prepareLoadingAdsDialog != null && prepareLoadingAdsDialog!!.isShowing && !activity.isDestroyed) prepareLoadingAdsDialog!!.dismiss()
                        adsManager.updateIsShowingFullScreenAd(false)
                        onAdsClosed("Activity life cycle is not valid.")
                    }
                }, 500)
            } else {
                onAdsClosed("Activity life cycle is not valid.")
            }
        } else {
            Timber.d("The interstitial ad wasn't ready yet.")
            loadAd()
            onAdsClosed("Interstitial ad wasn't ready yet.")
        }
    }
}
