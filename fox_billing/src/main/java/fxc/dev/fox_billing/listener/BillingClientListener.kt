package fxc.dev.fox_billing.listener

import fxc.dev.fox_billing.model.IAPProduct

interface BillingClientListener {
    fun onConnected(isConnected: Boolean, responseCode: Int)

    fun onQueryProductDetailComplete(products: List<IAPProduct>)

    fun onLaunchPurchaseComplete(isSuccess: Boolean)
}
