package fxc.dev.app.ui.settings

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import fxc.dev.app.databinding.ActivitySettingsBinding
import fxc.dev.app.ui.base.BaseActivity
import fxc.dev.app.ui.subscription.SubscriptionActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsActivity : BaseActivity<ActivitySettingsBinding, SettingsVM>() {
    override val viewModel: SettingsVM by viewModels()

    override fun setupViewBinding(inflater: LayoutInflater) =
        ActivitySettingsBinding.inflate(inflater)

    override fun init(savedInstanceState: Bundle?) {
        setupListeners()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isSubscribedState.collectLatest { isSubscribed ->
                    binding.cardSyncData.isVisible = !isSubscribed
                }
            }
        }
    }

    private fun setupListeners() {
        binding.btnSettingsBack.setOnClickListener { finish() }

        // Go Premium Banner
        binding.btnSyncGoPremium.setOnClickListener {
            startActivity(Intent(this, SubscriptionActivity::class.java))
        }

        // ACCOUNT
        binding.cardManageAccount.setOnClickListener { showToast("Opening Account Management...") }
        binding.cardManageSubscription.setOnClickListener {
            fxc.dev.app.util.SubscriptionUtils.showManageSubscriptionDialog(this)
        }
        binding.btnCopyUserId.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("User ID", "32c13eab511a90f2")
            clipboard.setPrimaryClip(clip)
            showToast("Copied User ID to clipboard!")
        }

        // GENERAL
        binding.cardTips.setOnClickListener { showToast("Opening Tips on taking pictures...") }
        binding.cardTellFriends.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Check out LeafSnap app for identifying plants!")
                type = "text/plain"
            }
            startActivity(Intent.createChooser(sendIntent, "Share LeafSnap"))
        }
        binding.cardRateReview.setOnClickListener { showToast("Opening Rate & Review...") }
        binding.cardFeedback.setOnClickListener { showToast("Opening Feedback...") }
        binding.cardFAQ.setOnClickListener { showToast("Opening FAQs...") }
        binding.cardRestorePurchase.setOnClickListener {
            lifecycleScope.launch {
                viewModel.restorePurchases()
                showToast("Restoring purchases from Google Play...")
            }
        }

        // PREFERENCES
        binding.cardLocation.setOnClickListener { showToast("Change Location (Current: Hao Nam)") }
        binding.cardLanguage.setOnClickListener { showToast("Change Language (Current: English)") }
        binding.cardUnits.setOnClickListener { showToast("Change Measurement Units (Current: Imperial)") }

        // LEGAL
        binding.cardPrivacyPolicy.setOnClickListener { showToast("Opening Privacy Policy...") }
        binding.cardTermOfUse.setOnClickListener { showToast("Opening Terms of Use...") }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
