package fxc.dev.fox_ads.appOpenAd

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import fxc.dev.fox_ads.AdsManager
import fxc.dev.fox_ads.databinding.ActivityWelcomeBackBinding

class WelcomeBackActivity : AppCompatActivity() {
    private val adsManager = AdsManager.getInstance()
    private val handler = Handler(Looper.getMainLooper())
    private val showAdRunnable = Runnable {
        adsManager.appOpenAdUtils.showAdIfAvailable(
            this,
            object : OnShowAdCompleteListener {
                override fun onShowAdComplete() {
                    finish()
                }
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWelcomeBackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Disable back pressed
        onBackPressedDispatcher.addCallback(this) { }
    }

    override fun onStart() {
        super.onStart()

        try {
            handler.postDelayed(showAdRunnable, 1000)
        } catch (e: Exception) {
            finish()
        }
    }

    override fun onStop() {
        super.onStop()

        handler.removeCallbacks(showAdRunnable)
    }
}
