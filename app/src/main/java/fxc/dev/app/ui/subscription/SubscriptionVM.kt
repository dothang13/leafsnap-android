package fxc.dev.app.ui.subscription

import dagger.hilt.android.lifecycle.HiltViewModel
import fxc.dev.app.ui.base.BaseViewModel
import fxc.dev.common.Fox
import fxc.dev.common.premium
import javax.inject.Inject

@HiltViewModel
class SubscriptionVM @Inject constructor() : BaseViewModel() {

    fun subscribe() {
        Fox.premium.updateSubscribedState(true)
    }
}
