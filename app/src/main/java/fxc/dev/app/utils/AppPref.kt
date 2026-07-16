package fxc.dev.app.utils

import com.chibatching.kotpref.KotprefModel

object AppPref: KotprefModel() {
    var hadTrackFirstTimeOpen by booleanPref()
}