package fxc.dev.fox_billing.listener

import fxc.dev.fox_billing.model.PurchaseInfo

interface PurchaseServiceListener {
    fun onProductPurchased(purchaseInfo: PurchaseInfo)

    fun onProductRestored(purchaseInfo: PurchaseInfo)

    fun onProductPurchasePending(purchaseInfo: PurchaseInfo)
}
