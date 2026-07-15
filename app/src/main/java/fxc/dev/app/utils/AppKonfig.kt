package fxc.dev.app.utils

import com.chibatching.remotekonfig.KonfigModel
import com.chibatching.remotekonfig.konfig

object AppKonfig : KonfigModel {
    val isShowWeek by konfig("a000_is_show_week", true)
    val isShowMonth by konfig("a000_is_show_month", true)
    val isShowYear by konfig("a000_is_show_year", true)
    val isShowOnetime by konfig("a000_is_show_onetime", true)
    val isShowAdLanguage by konfig("a000_is_show_ad_language", true)
    val isShowAdTheme by konfig("a000_is_show_ad_theme", true)
    val isShowAdIntro by konfig("a000_is_show_ad_intro", true)
    val isShowInterstitialAd by konfig("a000_is_show_interstitial_ad", true)
    val isShowAppOpenAd by konfig("a000_is_show_app_open_ad", true)
    val delayShowInternalInterstitial by konfig("a000_delay_show_interval_interstitial", 30000)
    val navigateReinstallingId by konfig("a000_navigate_reinstalling", "")
    val abBilling by konfig("a000_ab_billing", false)
}
