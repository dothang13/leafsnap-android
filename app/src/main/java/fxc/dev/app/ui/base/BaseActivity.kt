package fxc.dev.app.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.foxcode.android.common.nointernet.callbacks.ConnectionCallback
import com.foxcode.android.common.nointernet.ui.NoInternetDialog
import fxc.dev.app.R
import fxc.dev.fox_tracking.firebase.FirebaseScreenTracker
import kotlinx.coroutines.launch

abstract class BaseActivity<ViewBindingType : ViewBinding, ViewModelType : BaseViewModel> :
    AppCompatActivity(),
    ViewBindingHolder<ViewBindingType> by ViewBindingHolderImpl() {
    protected abstract val viewModel: ViewModelType

    protected val binding: ViewBindingType
        get() = requireBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            initBinding(
                binding = setupViewBinding(layoutInflater),
                lifecycle = lifecycle,
                className = this::class.simpleName,
                onBound = null
            )
        )
        handleOnBack()
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            applyInsets(systemBars)
            insets
        }
        init(savedInstanceState)
        setupNoInternetDialog()
    }

    override fun onResume() {
        super.onResume()
//        if (this !is SubscriptionActivity) {
//            Fox.tracking.lastScreenBeforeSubscription = this::class.java.simpleName
//        }
        FirebaseScreenTracker.trackScreen(this::class.java.simpleName) // track screen firebase analytics
    }

    open fun applyInsets(insets: Insets) {}

    open fun hasInternetConnection() {}

    private fun handleOnBack() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    private fun setupNoInternetDialog() {
        NoInternetDialog.Builder(this, lifecycle).apply {
            dialogProperties.apply {
                noInternetConnectionTitle = getString(R.string.no_internet_connection_title)
                noInternetConnectionMessage = getString(R.string.no_internet_connection_message)
                wifiOnButtonText = getString(R.string.turn_on_wifi_cta)
                mobileDataOnButtonText = getString(R.string.turn_on_mobile_data_cta)
                connectionCallback = object : ConnectionCallback {
                    override fun hasActiveConnection(hasActiveConnection: Boolean) {
                        if (hasActiveConnection) {
                            lifecycleScope.launch {
                                hasInternetConnection()
                            }
                        }
                    }
                }
            }
        }.build()
    }

    abstract fun init(savedInstanceState: Bundle?)

    abstract fun setupViewBinding(inflater: LayoutInflater): ViewBindingType
}
