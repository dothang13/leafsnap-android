package fxc.dev.app.util

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import fxc.dev.common.Fox
import fxc.dev.common.premium

object SubscriptionUtils {

    fun showManageSubscriptionDialog(activity: Activity) {
        val isCurrentlySubscribed = Fox.premium.isSubscribed()
        val statusBadge = if (isCurrentlySubscribed) "🌟 đang có Premium" else "⚪ chưa có Premium"

        val options = arrayOf(
            "🔴 [Test Dev] Hủy gia hạn Premium (Về bản Free)",
            "🟢 [Test Dev] Kích hoạt Premium (Giả lập Mua gói)",
            "🌐 Mở trang Quản lý gói trên Google Play Store"
        )

        AlertDialog.Builder(activity)
            .setTitle("Quản lý gói Premium ($statusBadge)")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> {
                        Fox.premium.updateSubscribedState(false)
                        Fox.premium.updateUnlockByCodeState(false)
                        Toast.makeText(
                            activity,
                            "Đã hủy gia hạn giả lập! (App đã về bản Free)",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    1 -> {
                        Fox.premium.updateSubscribedState(true)
                        Toast.makeText(
                            activity,
                            "Đã kích hoạt Premium giả lập thành công!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    2 -> {
                        openGooglePlaySubscriptions(activity)
                    }
                }
            }
            .setNegativeButton("Đóng", null)
            .show()
    }

    private fun openGooglePlaySubscriptions(activity: Activity) {
        val packageName = activity.packageName
        val subscriptionUrl = "https://play.google.com/store/account/subscriptions?package=$packageName"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(subscriptionUrl)).apply {
            setPackage("com.android.vending")
        }
        try {
            activity.startActivity(intent)
        } catch (e: Exception) {
            try {
                activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(subscriptionUrl)))
            } catch (ex: Exception) {
                Toast.makeText(activity, "Không thể mở CH Play trên thiết bị này", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
