package fxc.dev.fox_ads.base

import com.google.android.gms.ads.AdRequest
import fxc.dev.common.premium.IPremiumManager
import fxc.dev.fox_tracking.ITrackingManager
import fxc.dev.fox_tracking.adjust.IAdjustTokenConstants
import fxc.dev.fox_tracking.firebase.IFirebaseConstants

open class BaseAdUtils internal constructor(
    private val premiumManager: IPremiumManager,
    private val trackingManager: ITrackingManager
) {
    internal val defaultAdRequest: AdRequest
        get() = AdRequest.Builder().build()

    internal val adjustTokenConstants: IAdjustTokenConstants
        get() = trackingManager.getAdjustTokenConstants()

    internal val firebaseConstants: IFirebaseConstants
        get() = trackingManager.getFirebaseConstants()

    internal val appAllowShowAd: Boolean
        get() = !premiumManager.isSubscribed()
}
