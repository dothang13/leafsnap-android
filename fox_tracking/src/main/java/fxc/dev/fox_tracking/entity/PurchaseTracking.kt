package fxc.dev.fox_tracking.entity

/**
 * PurchaseTracking is a data class representing a purchase tracking information.
 *
 * @property productId The ID of the product.
 * @property price The price of the product.
 * @property currencyCode The currency code for the price.
 * @property contentType The content type of the purchase.
 * @property periods The purchase periods.
 * @property orderId The purchase orderId.
 * @property signature The purchase signature.
 * @property purchaseToken The purchase token.
 * @property purchaseTime The purchase time.
 * @property adjustTrackingId The Adjust tracking id of the purchase.
 */
data class PurchaseTracking(
    val productId: String,
    val price: Long,
    val currencyCode: String,
    val contentType: String,
    val periods: Periods,
    val orderId: String?,
    val signature: String,
    val purchaseToken: String,
    val purchaseTime: Long,
    val adjustTrackingId: String
) {
    /**
     * Periods is a enum class representing the purchase periods.
     *
     * @property value The value associated with the purchase period.
     */
    enum class Periods(
        val value: String
    ) {
        /**
         * Represents a weekly purchase period.
         */
        WEEKLY("weekly"),

        /**
         * Represents a monthly purchase period.
         */
        MONTHLY("monthly"),

        /**
         * Represents a yearly purchase period.
         */
        YEARLY("yearly"),

        /**
         * Represents a one-time purchase period.
         */
        ONE_TIME("one-time")
    }
}
