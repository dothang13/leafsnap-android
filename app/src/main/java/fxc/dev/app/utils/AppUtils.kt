package fxc.dev.app.utils

import android.content.Context
import android.telephony.TelephonyManager
import timber.log.Timber

object AppUtils {

    fun getCountryCodeFromNetwork(context: Context): String {
        try {
            val telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            return telephonyManager.networkCountryIso?.lowercase() ?: ""
        } catch (e: Exception) {
            Timber.d("${e.message}")
            return ""
        }
    }
}
