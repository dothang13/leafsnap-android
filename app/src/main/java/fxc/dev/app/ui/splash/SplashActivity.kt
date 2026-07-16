package fxc.dev.app.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.addCallback
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import fxc.dev.app.databinding.ActivitySplashBinding
import fxc.dev.app.ui.base.BaseActivity
import fxc.dev.app.ui.main.MainActivity
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
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
