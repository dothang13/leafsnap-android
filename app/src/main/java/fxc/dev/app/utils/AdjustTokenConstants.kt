package fxc.dev.app.utils

import fxc.dev.fox_tracking.adjust.IAdjustTokenConstants

object AdjustTokenConstants : IAdjustTokenConstants {
    // Key Adjust Token
    override val ADJUST_TOKEN = "test"

    // Key tracking billing event
    override val PURCHASE_KEY = "test"
    override val RESTORE_PURCHASE_KEY = "test"

    // Key tracking ads event
    override val AD_REVENUE = "test"
    override val BANNER_AD_CLICK = "test"
    override val BANNER_AD_IMPRESSION = "test"
    override val INTER_AD_CLICK = "test"
    override val INTER_AD_IMPRESSION = "test"
    override val NATIVE_AD_CLICK = "test"
    override val NATIVE_AD_IMPRESSION = "test"
    override val OPEN_AD_CLICK = "test"
    override val OPEN_AD_IMPRESSION = "test"
    override val REWARDED_AD_CLICK = "test"
    override val REWARDED_AD_IMPRESSION = "test"

    // Key tracking in app event
    override val WEEKLY_PURCHASED_KEY = "test"
    override val MONTHLY_PURCHASED_KEY = "test"
    override val YEARLY_PURCHASED_KEY = "test"
    override val ONETIME_PURCHASED_KEY = "test"

    // Key custom event
    override val FIRST_OPEN_ADJUST_KEY = "test"
}
