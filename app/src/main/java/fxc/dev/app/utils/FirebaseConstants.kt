package fxc.dev.app.utils

import fxc.dev.fox_tracking.firebase.IFirebaseConstants

object FirebaseConstants : IFirebaseConstants {
    // Firebase Analytics Event
    override val AD_REVENUE = "ad_revenue"

    // Firebase Analytics Parameter
    override val SCREEN_NAME = "screen_name"
    override val PRODUCT_ID = "product_id"
    override val AD_NETWORK = "ad_network"
    override val AD_TYPE = "ad_type"
    override val UNIT_ID = "ad_unit_id"
    override val SCREEN_SHOW_INAPP = "screen_show_inapp"
}
