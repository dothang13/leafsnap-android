package fxc.dev.fox_tracking.adjust

interface IAdjustTokenConstants {
    // Key Adjust Token
    val ADJUST_TOKEN: String

    // Key tracking billing event
    val PURCHASE_KEY: String
    val RESTORE_PURCHASE_KEY: String

    // Key tracking ads event
    val AD_REVENUE: String
    val BANNER_AD_CLICK: String
    val BANNER_AD_IMPRESSION: String
    val INTER_AD_CLICK: String
    val INTER_AD_IMPRESSION: String
    val NATIVE_AD_CLICK: String
    val NATIVE_AD_IMPRESSION: String
    val OPEN_AD_CLICK: String
    val OPEN_AD_IMPRESSION: String
    val REWARDED_AD_CLICK: String
    val REWARDED_AD_IMPRESSION: String

    // Key tracking in app event
    val WEEKLY_PURCHASED_KEY: String
    val MONTHLY_PURCHASED_KEY: String
    val YEARLY_PURCHASED_KEY: String
    val ONETIME_PURCHASED_KEY: String

    // Key custom event
    val FIRST_OPEN_ADJUST_KEY: String
}
