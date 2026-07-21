package fxc.dev.app.ui.subscription

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import fxc.dev.app.R
import fxc.dev.app.databinding.ActivitySubscriptionBinding
import fxc.dev.app.ui.base.BaseActivity
import fxc.dev.app.ui.main.MainActivity
import fxc.dev.fox_billing.model.IAPProduct
import fxc.dev.fox_billing.model.IAPProductPeriods
import fxc.dev.fox_billing.model.formattedPrice
import fxc.dev.fox_billing.model.periods
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SubscriptionActivity : BaseActivity<ActivitySubscriptionBinding, SubscriptionVM>() {
    override val viewModel: SubscriptionVM by viewModels()

    private var isAnnualSelected = true
    private var isFromSplash = false

    private var annualProduct: IAPProduct? = null
    private var weeklyProduct: IAPProduct? = null

    override fun setupViewBinding(inflater: LayoutInflater) =
        ActivitySubscriptionBinding.inflate(inflater)

    override fun init(savedInstanceState: Bundle?) {
        isFromSplash = intent.getBooleanExtra(EXTRA_FROM_SPLASH, false)

        setupClickListeners()
        updateCardSelectionStates()
        observeViewModel()
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
            val selectedProduct = if (isAnnualSelected) annualProduct else weeklyProduct
            if (selectedProduct != null && selectedProduct.productDetails != null) {
                viewModel.buyProduct(this, selectedProduct)
            } else {
                Toast.makeText(this, "Activating trial/subscription...", Toast.LENGTH_SHORT).show()
                viewModel.mockSubscribe()
            }
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
            viewModel.restorePurchases()
        }
        binding.tvManageSubscription.setOnClickListener {
            fxc.dev.app.util.SubscriptionUtils.showManageSubscriptionDialog(this)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.iapProducts.collectLatest { products ->
                        if (products.isNotEmpty()) {
                            bindProducts(products)
                        }
                    }
                }
                launch {
                    viewModel.isSubscribedState.collectLatest { isSubscribed ->
                        if (isSubscribed) {
                            Toast.makeText(
                                this@SubscriptionActivity,
                                R.string.upgrade_premium_successfully_message,
                                Toast.LENGTH_SHORT
                            ).show()
                            navigateNext()
                        }
                    }
                }
            }
        }
    }

    private fun bindProducts(products: List<IAPProduct>) {
        val yearSubId = getString(R.string.billing_sub_year)
        val weekSubId = getString(R.string.billing_sub_week)

        annualProduct = products.firstOrNull { it.productId == yearSubId }
            ?: products.firstOrNull { it.periods() == IAPProductPeriods.Yearly }

        weeklyProduct = products.firstOrNull { it.productId == weekSubId }
            ?: products.firstOrNull { it.periods() == IAPProductPeriods.Weekly }

        annualProduct?.formattedPrice()?.let { price ->
            binding.tvPriceAnnual.text = "Just $price/year"
        }

        weeklyProduct?.formattedPrice()?.let { price ->
            binding.tvPriceWeekly.text = price
        }

        updateCardSelectionStates()
    }

    private fun updateCardSelectionStates() {
        if (isAnnualSelected) {
            // Highlight Annual card
            binding.cardAnnual.setBackgroundResource(R.drawable.bg_subscription_card_selected)
            binding.ivRadioAnnual.setImageResource(R.drawable.ic_circle_checked)

            // Dim Weekly card
            binding.cardWeekly.setBackgroundResource(R.drawable.bg_subscription_card_unselected)
            binding.ivRadioWeekly.setImageResource(R.drawable.ic_circle_outline)

            val annual = annualProduct
            if (annual != null && annual.hasFreeTrial) {
                binding.tvTrialHint.text = "${annual.freeTrialDays}-days FREE trial then ${annual.formattedPrice() ?: ""}"
            }
        } else {
            // Dim Annual card
            binding.cardAnnual.setBackgroundResource(R.drawable.bg_subscription_card_unselected)
            binding.ivRadioAnnual.setImageResource(R.drawable.ic_circle_outline)

            // Highlight Weekly card
            binding.cardWeekly.setBackgroundResource(R.drawable.bg_subscription_card_selected)
            binding.ivRadioWeekly.setImageResource(R.drawable.ic_circle_checked)

            val weekly = weeklyProduct
            if (weekly != null && weekly.hasFreeTrial) {
                binding.tvTrialHint.text = "${weekly.freeTrialDays}-days FREE trial then ${weekly.formattedPrice() ?: ""}"
            }
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

