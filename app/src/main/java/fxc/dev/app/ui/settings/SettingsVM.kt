package fxc.dev.app.ui.settings

import dagger.hilt.android.lifecycle.HiltViewModel
import fxc.dev.app.ui.base.BaseViewModel
import fxc.dev.common.Fox
import fxc.dev.common.premium
import fxc.dev.fox_billing.billing
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsVM @Inject constructor() : BaseViewModel() {
    val isSubscribedState: StateFlow<Boolean> = Fox.premium.getSubscribedStateFlow()

    suspend fun restorePurchases() {
        Fox.billing.queryPurchases()
    }
}
