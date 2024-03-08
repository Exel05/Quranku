package com.xellagon.quranku.data.source.local

import com.chibatching.kotpref.KotprefModel

object GlobalPreferences : KotprefModel() {
    var firstTime by booleanPref(false)
}
