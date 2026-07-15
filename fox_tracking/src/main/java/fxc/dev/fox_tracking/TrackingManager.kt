package fxc.dev.fox_tracking

import android.app.Application
import fxc.dev.fox_tracking.adjust.AdjustEventTracking
import fxc.dev.fox_tracking.adjust.IAdjustTokenConstants
import fxc.dev.fox_tracking.appsflyer.AppsFlyerEventTracking
import fxc.dev.fox_tracking.firebase.FirebaseEventTracking
import fxc.dev.fox_tracking.firebase.IFirebaseConstants
import fxc.dev.fox_tracking.inHouse.InHouseEventTracking

/**
 * TrackingManager is a class that manages event tracking and provides methods for logging purchase events and custom events.
 */
class TrackingManager private constructor() : ITrackingManager() {

    var lastScreenBeforeSubscription: String? = null

    override fun configure(
        application: Application,
        applicationId: String,
        adjustTokenConstants: IAdjustTokenConstants,
        firebaseConstants: IFirebaseConstants,
        appsFlyerId: String,
        trackingUrl: String
    ) {
        super.configure(
            application,
            applicationId,
            adjustTokenConstants,
            firebaseConstants,
            appsFlyerId,
            trackingUrl
        )
        add(AdjustEventTracking(application, adjustTokenConstants))
        add(AppsFlyerEventTracking(application, appsFlyerId))
        add(InHouseEventTracking(applicationId, trackingUrl))
        add(FirebaseEventTracking(firebaseConstants))
    }

    companion object {
        @Volatile
        private var instance: TrackingManager? = null

        /**
         * Returns the singleton instance of TrackingManager.
         *
         * @return The TrackingManager instance.
         */
        internal fun getInstance(): TrackingManager = instance ?: synchronized(this) {
            instance ?: TrackingManager().also { instance = it }
        }
    }
}
