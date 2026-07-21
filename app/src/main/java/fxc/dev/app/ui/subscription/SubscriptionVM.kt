package fxc.dev.app.ui.subscription

import android.app.Activity
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fxc.dev.app.ui.base.BaseViewModel
import fxc.dev.common.Fox
import fxc.dev.common.premium
import fxc.dev.fox_billing.billing
import fxc.dev.fox_billing.model.IAPProduct
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionVM @Inject constructor() : BaseViewModel() {

    val iapProducts: StateFlow<List<IAPProduct>> = Fox.billing.iapProductsFlow
    val isSubscribedState: StateFlow<Boolean> = Fox.premium.getSubscribedStateFlow()

    fun buyProduct(activity: Activity, product: IAPProduct) {
        Fox.billing.buyBasePlan(activity, product)
    }

    fun restorePurchases() {
        viewModelScope.launch {
            Fox.billing.queryPurchases()
        }
    }

    fun mockSubscribe() {
        Fox.premium.updateSubscribedState(true)
    }
}

