package fxc.dev.fox_ads.extensions

import android.app.Activity
import android.graphics.Color

fun Activity.hideStatusBar() {
    window.statusBarColor = Color.BLACK
}

fun Activity.restoreStatusBar() {
    window.statusBarColor = Color.TRANSPARENT
}


