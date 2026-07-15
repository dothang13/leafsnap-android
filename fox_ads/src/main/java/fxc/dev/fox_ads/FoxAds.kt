package fxc.dev.fox_ads

import fxc.dev.common.Fox

val Fox.ads: AdsManager
    get() = AdsManager.getInstance()
