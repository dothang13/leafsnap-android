package fxc.dev.fox_billing

import fxc.dev.common.Fox
import fxc.dev.fox_billing.manager.BillingManager

val Fox.billing: BillingManager
    get() = BillingManager.getInstance()
