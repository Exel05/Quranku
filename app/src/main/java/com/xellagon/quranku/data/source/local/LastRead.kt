package com.xellagon.quranku.data.source.local

import com.chibatching.kotpref.KotprefModel

object LastRead : KotprefModel() {
    var surahNumber by intPref()
    var position by intPref()
    var surahName by stringPref()
    var ayahSurah by intPref()
    var ayahText by stringPref()
    var trasnlate by stringPref()
}