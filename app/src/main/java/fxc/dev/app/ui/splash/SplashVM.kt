package fxc.dev.app.ui.splash

import dagger.hilt.android.lifecycle.HiltViewModel
import fxc.dev.app.ui.base.BaseViewModel
import fxc.dev.common.Fox
import fxc.dev.common.premium
import javax.inject.Inject

@HiltViewModel
class SplashVM
    @Inject
    constructor() : BaseViewModel() {
        val isSubscribed: Boolean
            get() = Fox.premium.isSubscribed()
    }
