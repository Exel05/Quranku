package com.xellagon.quranku.ui.screens.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xellagon.quranku.data.repository.QoranRepository
import com.xellagon.quranku.data.source.local.entity.Jozz
import com.xellagon.quranku.data.source.local.entity.Page
import com.xellagon.quranku.data.source.local.entity.Qoran
import com.xellagon.quranku.data.source.local.entity.Surah
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository : QoranRepository
) : ViewModel() {

    private val _searchSurah = MutableStateFlow("")
    val searchSurah = _searchSurah.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _qoranList = MutableStateFlow(emptyList<Surah>())
    val qoranList = _qoranList.asStateFlow()

    private val _listSearchQoran = MutableStateFlow(emptyList<Qoran>())
    val listSearchQoran = _listSearchQoran.asStateFlow()
//    ini quran bambang bukan surat List<Qoran> bikin lagi search list buat surat

    private val _surahListState = mutableStateOf(emptyList<Surah>())
    val surahListState = _surahListState

    private val _juzListState = mutableStateOf(emptyList<Jozz>())
    val juzListState = _juzListState

    private val _pageListState = mutableStateOf(emptyList<Page>())
    val pageListState = _pageListState

    private val _searchAyahState = MutableStateFlow(emptyList<Qoran>())
    val searchAyahState = _searchAyahState.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val seacrhText = _searchText.asStateFlow()

    private var getSurahJob : Job? = null
    private var getJuzJob : Job? = null
    private var getPageJob : Job? = null

    fun searchResult(surah: String) {
        repository.searchSurah(surah).onEach {
            _listSearchQoran.emit(it)
        }.launchIn(viewModelScope)

        repository.seacrhAyah(surah).onEach {
            _searchAyahState.emit(it)
        }.launchIn(viewModelScope)
    }

    fun onSearchTextChange(text : String) {
        _searchText.value = text
    }

    fun onSearchSurahChange(surah : String) {
        _searchSurah.value = surah
    }

    fun onToogleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onSearchSurahChange("")
        }
    }

    private fun getAllQuranIndex() {
        getSurahJob?.cancel()
        getJuzJob?.cancel()
        getPageJob?.cancel()

        getSurahJob = repository.getQuranIndexBySurah().onEach {
            Log.d("SURAH", it.toString())
            _surahListState.value = it
        }.launchIn(viewModelScope)

        getJuzJob = repository.getQuranIndexByJuz().onEach {
            _juzListState.value = it
        }.launchIn(viewModelScope)

        getPageJob = repository.getQuranIndexByPage().onEach {
            _pageListState.value = it
        }.launchIn(viewModelScope)
    }

    init {
        getAllQuranIndex()
    }

}