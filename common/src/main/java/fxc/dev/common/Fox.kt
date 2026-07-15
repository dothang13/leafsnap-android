package fxc.dev.common

import fxc.dev.common.premium.PremiumManager

object Fox

val Fox.premium: PremiumManager
    get() = PremiumManager.INSTANCE