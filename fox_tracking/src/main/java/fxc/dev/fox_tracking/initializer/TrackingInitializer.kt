package fxc.dev.fox_tracking.initializer

import android.content.Context
import androidx.startup.Initializer
import fxc.dev.common.utils.timber.TimberInitializer
import fxc.dev.fox_tracking.ITrackingManager
import fxc.dev.fox_tracking.TrackingManager
import timber.log.Timber

/**
 * TrackingInitializer is a class that initializes the TrackingManager instance as part of the application startup process.
 */
class TrackingInitializer : Initializer<ITrackingManager> {
    override fun create(context: Context): ITrackingManager = TrackingManager.getInstance().apply {
        Timber.tag("Initializer").d("TrackingManager initialized successfully!")
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf(
        TimberInitializer::class.java
    )
}
