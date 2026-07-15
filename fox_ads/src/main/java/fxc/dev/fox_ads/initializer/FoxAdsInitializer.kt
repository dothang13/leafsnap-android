package fxc.dev.fox_ads.initializer

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import fxc.dev.common.Fox
import fxc.dev.common.premium
import fxc.dev.common.premium.PremiumInitializer
import fxc.dev.common.utils.timber.TimberInitializer
import fxc.dev.fox_ads.AdsManager
import fxc.dev.fox_tracking.initializer.TrackingInitializer
import fxc.dev.fox_tracking.tracking
import timber.log.Timber

/**
 * FoxAdsInitializer is a class that initializes the AdsManager instance as part of the application startup process.
 */
class FoxAdsInitializer : Initializer<AdsManager> {
    override fun create(context: Context): AdsManager = AdsManager
        .initialize(
            application = context as Application,
            trackingManager = Fox.tracking,
            premiumManager = Fox.premium
        ).apply {
            Timber.tag("Initializer").d("AdsManager initialized successfully!")
        }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf(
        TimberInitializer::class.java,
        PremiumInitializer::class.java,
        TrackingInitializer::class.java
    )
}
