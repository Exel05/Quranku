package com.xellagon.quranku.di

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.xellagon.quranku.data.source.local.SettingsPreferences

object GlobalState {
    var isOnBoarding by mutableStateOf(SettingsPreferences.isOnBoarding)
}