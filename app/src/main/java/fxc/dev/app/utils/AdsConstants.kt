package fxc.dev.app.utils

import android.content.res.Resources
import fxc.dev.app.MainApplication
import fxc.dev.app.R
import fxc.dev.fox_ads.constants.AbstractAdsConstants

object AdsConstants : AbstractAdsConstants() {
    private val resources: Resources
        get() = MainApplication.instance.resources

    override val ADMOB_NATIVE_ID = resources.getString(R.string.ads_native_id)
    override val ADMOB_INTERSTITIAL_ID = resources.getString(R.string.ads_interstitial_id)
    override val ADMOB_BACKWARD_INTERSTITIAL_ID = resources.getString(R.string.ads_interstitial_backward_id)
    override val ADMOB_BANNER_ID = resources.getString(R.string.ads_banner_id)
    override val ADMOB_REWARDED_ID = resources.getString(R.string.ads_rewarded_id)
    override val ADMOB_APP_OPEN_ID = resources.getString(R.string.ads_open_ads_id)
}