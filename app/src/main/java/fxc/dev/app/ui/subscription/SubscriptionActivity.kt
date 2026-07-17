package fxc.dev.app.ui.subscription

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import fxc.dev.app.R
import fxc.dev.app.databinding.ActivitySubscriptionBinding
import fxc.dev.app.ui.base.BaseActivity
import fxc.dev.app.ui.main.MainActivity

@AndroidEntryPoint
class SubscriptionActivity : BaseActivity<ActivitySubscriptionBinding, SubscriptionVM>() {
    override val viewModel: SubscriptionVM by viewModels()

    private var isAnnualSelected = true
    private var isFromSplash = false

    override fun setupViewBinding(inflater: LayoutInflater) =
        ActivitySubscriptionBinding.inflate(inflater)

    override fun init(savedInstanceState: Bundle?) {
        isFromSplash = intent.getBooleanExtra(EXTRA_FROM_SPLASH, false)

        setupClickListeners()
        updateCardSelectionStates()
    }

    private fun setupClickListeners() {
        // Close Button
        binding.ivClose.setOnClickListener {
            navigateNext()
        }

        // Annual Card Click
        binding.cardAnnual.setOnClickListener {
            isAnnualSelected = true
            updateCardSelectionStates()
        }

        // Weekly Card Click
        binding.cardWeekly.setOnClickListener {
            isAnnualSelected = false
            updateCardSelectionStates()
        }

        // Start Subscription CTA
        binding.btnStartSubscription.setOnClickListener {
            viewModel.subscribe()
            Toast.makeText(this, "Subscription activated successfully!", Toast.LENGTH_SHORT).show()
            navigateNext()
        }

        // Legal Links
        binding.tvPrivacyPolicy.setOnClickListener {
            Toast.makeText(this, "Opening Privacy Policy...", Toast.LENGTH_SHORT).show()
        }
        binding.tvTermsOfUse.setOnClickListener {
            Toast.makeText(this, "Opening Terms Of Use...", Toast.LENGTH_SHORT).show()
        }
        binding.tvRestore.setOnClickListener {
            Toast.makeText(this, "Restoring your purchase...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateCardSelectionStates() {
        if (isAnnualSelected) {
            // Highlight Annual card
            binding.cardAnnual.setBackgroundResource(R.drawable.bg_subscription_card_selected)
            binding.ivRadioAnnual.setImageResource(R.drawable.ic_circle_checked)

            // Dim Weekly card
            binding.cardWeekly.setBackgroundResource(R.drawable.bg_subscription_card_unselected)
            binding.ivRadioWeekly.setImageResource(R.drawable.ic_circle_outline)
        } else {
            // Dim Annual card
            binding.cardAnnual.setBackgroundResource(R.drawable.bg_subscription_card_unselected)
            binding.ivRadioAnnual.setImageResource(R.drawable.ic_circle_outline)

            // Highlight Weekly card
            binding.cardWeekly.setBackgroundResource(R.drawable.bg_subscription_card_selected)
            binding.ivRadioWeekly.setImageResource(R.drawable.ic_circle_checked)
        }
    }

    private fun navigateNext() {
        if (isFromSplash) {
            startActivity(Intent(this, MainActivity::class.java))
        }
        finish()
    }

    companion object {
        const val EXTRA_FROM_SPLASH = "extra_from_splash"
    }
}
