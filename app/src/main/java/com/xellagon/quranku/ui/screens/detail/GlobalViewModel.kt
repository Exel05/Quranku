package com.xellagon.quranku.ui.screens.detail

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.xellagon.quranku.data.source.local.entity.Surah

class GlobalViewModel : ViewModel() {

    private val _totalAyah = mutableStateListOf<Int>()

    fun setTotalAyah(surahList : List<Surah>) {
        val totalAyah = mutableStateListOf<Int>()
        surahList.forEach {
            totalAyah.add(it.numberOfAyah!!)
        }
        _totalAyah.addAll(totalAyah)
    }

    fun getTotalAyah() = _totalAyah.toList()

}