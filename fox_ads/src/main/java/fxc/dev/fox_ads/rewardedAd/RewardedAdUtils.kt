package fxc.dev.fox_ads.rewardedAd

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import fxc.dev.common.premium.IPremiumManager
import fxc.dev.fox_ads.AdsManager
import fxc.dev.fox_ads.PrepareLoadingAdsDialog
import fxc.dev.fox_ads.base.BaseAdUtils
import fxc.dev.fox_ads.extensions.hideStatusBar
import fxc.dev.fox_ads.extensions.restoreStatusBar
import fxc.dev.fox_ads.extensions.trackAdRevenueOnPaid
import fxc.dev.fox_ads.ump.GoogleMobileAdsConsentManager
import fxc.dev.fox_tracking.AdEventConstants
import fxc.dev.fox_tracking.AdTypeConstants
import fxc.dev.fox_tracking.EventKeyConstants
import fxc.dev.fox_tracking.EventTypeConstants
import fxc.dev.fox_tracking.ITrackingManager
import timber.log.Timber

class RewardedAdUtils internal constructor(
    private val rewardedAdId: String,
    private val adsManager: AdsManager,
    private val premiumManager: IPremiumManager,
    private val trackingManager: ITrackingManager,
    private val googleMobileAdsConsentManager: GoogleMobileAdsConsentManager
) : BaseAdUtils(premiumManager, trackingManager) {
    private var prepareLoadingAdsDialog: PrepareLoadingAdsDialog? = null

    fun showRewardedAd(
        activity: AppCompatActivity,
        onAdShowed: () -> Unit,
        onAdDismissed: () -> Unit,
        onLoadFailed: () -> Unit,
        onRewarded: (RewardItem) -> Unit
    ) {
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

            loadRewardedAd(
                context = activity,
                onAdLoaded = { rewardedAd ->
                    Handler(Looper.getMainLooper()).postDelayed({
                        if (activity.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                            Handler(Looper.getMainLooper()).postDelayed(
                                {
                                    if (prepareLoadingAdsDialog != null && prepareLoadingAdsDialog!!.isShowing && !activity.isDestroyed) prepareLoadingAdsDialog!!.dismiss()
                                },
                                1000
                            )

                            rewardedAd.fullScreenContentCallback =
                                object : FullScreenContentCallback() {
                                    override fun onAdDismissedFullScreenContent() {
                                        Timber.d("Ad dismissed full screen content.")
                                        adsManager.updateIsShowingFullScreenAd(false)
                                        activity.restoreStatusBar()
                                        onAdDismissed()
                                    }

                                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                                        Timber.e("Ad failed to show full screen content: ${adError.message}")
                                        adsManager.updateIsShowingFullScreenAd(false)
                                        activity.restoreStatusBar()
                                        onLoadFailed()
                                    }

                                    override fun onAdShowedFullScreenContent() {
                                        Timber.d("Ad showed fullscreen content.")
                                        activity.hideStatusBar()
                                        onAdShowed()
                                    }

                                    override fun onAdClicked() {
                                        super.onAdClicked()
                                        val firebaseBundle = bundleOf(
                                            firebaseConstants.SCREEN_NAME to activity::class.java.simpleName,
                                            firebaseConstants.AD_TYPE to AdTypeConstants.REWARDED,
                                            firebaseConstants.UNIT_ID to rewardedAdId,
                                            EventKeyConstants.EVENT_NAME to AdEventConstants.REWARDED_AD_CLICK
                                        )
                                        trackingManager.logCustomEvent(
                                            bundleOf(
                                                EventTypeConstants.ADJUST to adjustTokenConstants.REWARDED_AD_CLICK,
                                                EventTypeConstants.FIREBASE to firebaseBundle
                                            )
                                        )
                                    }

                                    override fun onAdImpression() {
                                        super.onAdImpression()
                                        val firebaseBundle = bundleOf(
                                            firebaseConstants.SCREEN_NAME to activity::class.java.simpleName,
                                            firebaseConstants.AD_TYPE to AdTypeConstants.REWARDED,
                                            firebaseConstants.UNIT_ID to rewardedAdId,
                                            EventKeyConstants.EVENT_NAME to AdEventConstants.REWARDED_AD_IMPRESSION
                                        )
                                        trackingManager.logCustomEvent(
                                            bundleOf(
                                                EventTypeConstants.ADJUST to adjustTokenConstants.REWARDED_AD_IMPRESSION,
                                                EventTypeConstants.FIREBASE to firebaseBundle
                                            )
                                        )
                                    }
                                }
                            rewardedAd.show(activity) {
                                Timber.d("User earned the reward.")
                                onRewarded(it)
                            }
                        } else {
                            if (prepareLoadingAdsDialog != null && prepareLoadingAdsDialog!!.isShowing && !activity.isDestroyed) prepareLoadingAdsDialog!!.dismiss()
                            adsManager.updateIsShowingFullScreenAd(false)
                            onLoadFailed()
                        }
                    }, 500)
                },
                onLoadFailed = {
                    if (prepareLoadingAdsDialog != null && prepareLoadingAdsDialog!!.isShowing && !activity.isDestroyed) prepareLoadingAdsDialog!!.dismiss()
                    adsManager.updateIsShowingFullScreenAd(false)
                    onLoadFailed()
                }
            )
        } else {
            onLoadFailed()
        }
    }

    private fun loadRewardedAd(
        context: Context,
        onAdLoaded: (RewardedAd) -> Unit,
        onLoadFailed: (() -> Unit)?
    ) {
        if (!googleMobileAdsConsentManager.canRequestAds) {
            Timber.d("Mobile Ads consent manager cannot request ads.")
            onLoadFailed?.invoke()
            return
        }

        RewardedAd.load(
            context,
            rewardedAdId,
            defaultAdRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Timber.e("Ad failed to load, domain: ${adError.domain}, code: ${adError.code}, message: ${adError.message}.")
                    onLoadFailed?.invoke()
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    Timber.d("Ad was loaded.")
                    rewardedAd.trackAdRevenueOnPaid(trackingManager)
                    onAdLoaded(rewardedAd)
                }
            }
        )
    }
}
