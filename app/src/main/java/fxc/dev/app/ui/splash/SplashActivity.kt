package fxc.dev.app.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.addCallback
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.lifecycleScope
import fxc.dev.app.databinding.ActivitySplashBinding
import fxc.dev.app.ui.base.BaseActivity
import fxc.dev.app.ui.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashVM>() {
    override val viewModel: SplashVM by viewModels()

    override fun setupViewBinding(inflater: LayoutInflater) =
        ActivitySplashBinding.inflate(inflater)

    override fun init(savedInstanceState: Bundle?) {
        onBackPressedDispatcher.addCallback {
            finishAffinity()
            exitProcess(0)
        }
        goToMain()
    }


    private fun goToMain() {
        lifecycleScope.launch {
            delay(2000) // Hiển thị Splash Screen trong 2 giây
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            @Suppress("DEPRECATION")
            overridePendingTransition(android.R.anim.fade_in, 0)
        }
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}
