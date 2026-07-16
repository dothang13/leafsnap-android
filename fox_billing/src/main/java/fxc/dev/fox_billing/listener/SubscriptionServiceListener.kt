package fxc.dev.fox_billing.listener

import fxc.dev.fox_billing.model.PurchaseInfo

interface SubscriptionServiceListener {
    fun onSubscriptionRestored(purchaseInfo: PurchaseInfo)

    fun onSubscriptionPurchased(purchaseInfo: PurchaseInfo)

    fun onSubscriptionPurchasePending(purchaseInfo: PurchaseInfo)
}
