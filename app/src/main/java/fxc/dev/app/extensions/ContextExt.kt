package fxc.dev.app.extensions

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import fxc.dev.app.R
import timber.log.Timber
import androidx.core.net.toUri

fun Context.openAppSystemSettings() {
    startActivity(
        Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", packageName, null)
        }
    )
}

fun Context.openLocationServiceSetting() {
    startActivity(
        Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    )
}

fun Context.openWifiSetting() {
    startActivity(
        Intent(Settings.ACTION_WIFI_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    )
}

fun Context.openHotspotTetheringSetting() {
    startActivity(
        Intent(Intent.ACTION_MAIN, null)
            .addCategory(Intent.CATEGORY_LAUNCHER)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .setComponent(
                ComponentName(
                    "com.android.settings",
                    "com.android.settings.TetherSettings"
                )
            )
    )
}

fun Context.openWriteSystemSettings() {
    startActivity(
        Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS).apply {
            data = "package:$packageName".toUri()
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    )
}

fun Context.shareMyAppStore() {
    val appName = getString(R.string.app_name)
    val shareBodyText = "https://play.google.com/store/apps/details?id=${packageName}"
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TITLE, appName)
        putExtra(Intent.EXTRA_TEXT, shareBodyText)
    }
    startActivity(Intent.createChooser(sendIntent, null))
}

fun Context.linkToChPlay(idApp: String = packageName) {
    try {
        val marketIntent = Intent(Intent.ACTION_VIEW, "market://details?id=$idApp".toUri())
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            "https://play.google.com/store/apps/details?id=$idApp".toUri()
        )

        if (marketIntent.resolveActivity(packageManager) != null) {
            startActivity(marketIntent)
        } else if (webIntent.resolveActivity(packageManager) != null) {
            startActivity(webIntent)
        }
    } catch (_: Exception) {
    }
}

fun Context.openWebPage(url: String, noActivityFound: () -> Unit = {}) {
    try {
        var newUrl = url
        if (!newUrl.startsWith("http://") && !newUrl.startsWith("https://")) {
            newUrl = "http://$newUrl"
        }
        Timber.d("Open Web Page url = $newUrl")
        val webpage = newUrl.toUri()
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            noActivityFound()
        }
    } catch (e: Exception) {
        noActivityFound()
    }
}
