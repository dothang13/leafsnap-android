package fxc.dev.fox_tracking.firebase

import android.os.Bundle
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics

object FirebaseScreenTracker : DefaultLifecycleObserver {
    private var lastScreen: String? = null

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    fun trackScreen(screen: String) {
        Firebase.analytics.logEvent(
            FirebaseAnalytics.Event.SCREEN_VIEW,
            Bundle().apply { putString(FirebaseAnalytics.Param.SCREEN_NAME, screen) }
        )
        lastScreen = screen
    }

    private fun trackAppExit() {
        lastScreen?.let {
            Firebase.analytics.logEvent(
                "app_exit",
                Bundle().apply { putString(FirebaseAnalytics.Param.SCREEN_NAME, it) }
            )
        }
    }

    /*---------- LifecycleObserver ----------*/

    override fun onStop(owner: LifecycleOwner) {
        trackAppExit()
    }
}
