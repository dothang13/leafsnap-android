package fxc.dev.fox_billing.initializer

import android.content.Context
import androidx.startup.Initializer
import fxc.dev.common.Fox
import fxc.dev.common.premium
import fxc.dev.common.premium.PremiumInitializer
import fxc.dev.common.utils.timber.TimberInitializer
import fxc.dev.fox_billing.manager.BillingManager
import fxc.dev.fox_tracking.initializer.TrackingInitializer
import fxc.dev.fox_tracking.tracking
import timber.log.Timber

/**
 * BillingInitializer is a class that initializes the BillingManager instance as part of the application startup process.
 */
class BillingInitializer : Initializer<BillingManager> {
    override fun create(context: Context): BillingManager = BillingManager
        .initialize(
            applicationContext = context,
            trackingManager = Fox.tracking,
            premiumManager = Fox.premium
        ).apply {
            Timber.tag("Initializer").d("BillingManager initialized successfully!")
        }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf(
        TimberInitializer::class.java,
        PremiumInitializer::class.java,
        TrackingInitializer::class.java
    )
}
