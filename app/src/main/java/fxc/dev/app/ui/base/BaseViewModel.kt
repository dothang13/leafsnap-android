package fxc.dev.app.ui.base

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fxc.dev.common.Fox
import fxc.dev.common.premium
import fxc.dev.fox_ads.ads
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel
    @Inject
    constructor() : ViewModel() {
        val hasPurchased by lazy {
            Fox.premium.getSubscribedStateFlow()
        }

        val nativeAdsFlow by lazy {
            Fox.ads.nativeAdUtils.nativeAdsFlow
        }
    }
