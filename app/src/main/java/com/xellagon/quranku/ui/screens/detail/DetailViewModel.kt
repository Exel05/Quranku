package com.xellagon.quranku.ui.screens.detail

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xellagon.quranku.data.repository.QoranRepository
import com.xellagon.quranku.data.source.local.SettingsPreferences
import com.xellagon.quranku.data.source.local.entity.Bookmark
import com.xellagon.quranku.data.source.local.entity.Qoran
import com.xellagon.quranku.ui.screens.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import snow.player.PlayMode
import snow.player.PlayerClient
import snow.player.audio.MusicItem
import snow.player.playlist.Playlist
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository : QoranRepository,
    savedStateHandle: SavedStateHandle,
    private val playerClient: PlayerClient
) : ViewModel() {

    val navArgs : ReadArguments = savedStateHandle.navArgs()
    val position = navArgs.position


    private val _ayahList = MutableStateFlow<List<Qoran>>(emptyList())
    val ayahList = _ayahList
        .asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(500), emptyList())

    fun addBookmark(bookmark: Bookmark){
        viewModelScope.launch {
            repository.insertBookmark(bookmark)
        }
    }

    fun load() {
        when(navArgs.readType) {
            0 -> {
                repository.readAyahBySurahNumber(navArgs.surahNumber ?: 0).onEach { list ->
                    _ayahList.emit(list)
                }.launchIn(viewModelScope)
            }
            1 -> {
                repository.readAyahByJuzNumber(navArgs.juzNumber ?: 0).onEach { list ->
                    Log.d("LIST", list.toString())
                    _ayahList.emit(list)
                }.launchIn(viewModelScope)
            }
            2 -> {
                repository.readAyahByPageNumber(navArgs.pageNumber ?: 0).onEach { list ->
                    _ayahList.emit(list)
                }.launchIn(viewModelScope)
            }
            else -> {}
        }
    }

    init {
        load()
    }

    fun onEvent(event : ReadScreenEvent) {
        when(event) {
            is ReadScreenEvent.playAyah -> {
                playerClient.stop()
                playerClient.connect {
                    val playList = onePlayList(
                        event.qoran
                    )
                    qoran.value = event.qoran
                    playerClient.setPlaylist(playList, true)
                    playMode.value = PlayerMode.IS_PLAYING
                }
            }
            is ReadScreenEvent.playAllAyah -> {
                playerClient.stop()
                playerClient.connect {
                    val playlist = allPlayList(event.qoranList)
                    playerClient.setPlaylist(playlist, true)
                    playMode.value = PlayerMode.IS_PLAYING
                    playerClient.playMode = PlayMode.PLAYLIST_LOOP
                    playerClient.addOnPlayingMusicItemChangeListener{_, position, _ ->
                        qoran.value = event.qoranList[position]
                    }

                }
            }
            is ReadScreenEvent.PauseAyah -> {
                playerClient.playPause()
                if (playMode.value == PlayerMode.PAUSED) {
                    PlayerMode.IS_PLAYING
                } else {
                    PlayerMode.PAUSED
                }
            }
            is ReadScreenEvent.NextAyah -> {
                playerClient.skipToNext()
            }
            is ReadScreenEvent.PrevAyah -> {
                playerClient.skipToPrevious()
            }
            is ReadScreenEvent.StopAyah -> {
                playerClient.stop()
                playMode.value = PlayerMode.NOT_PLAYING

            }
        }
    }

    val playMode = mutableStateOf(PlayerMode.NOT_PLAYING)
    val qoran = mutableStateOf<Qoran?>(null)

    private fun allPlayList(
        qoranList : List<Qoran>
    )  : Playlist {

        val musicItemList = mutableListOf<MusicItem>()

        qoranList.forEach { qoran ->
            val formattedSurahNumber = String.format("%03d", qoran.surahNumber)
            val formattedAyahNumber = String.format("%03d", qoran.ayahNumber)

            musicItemList.add(
                MusicItem.Builder()
                    .setTitle("${qoran.ayahNumber} - ${qoran.surahNumber}")
                    .setArtist("")
                    .setUri("https://everyayah.com/data/${SettingsPreferences.listQori[SettingsPreferences.currentQori].qoriId}/$formattedSurahNumber$formattedAyahNumber.mp3")
                    .autoDuration()
                    .setIconUri("https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Abdelbasset-abdessamad-27.jpg/640px-Abdelbasset-abdessamad-27.jpg")
                    .build()
            )
        }

        return Playlist.Builder()
            .appendAll(musicItemList)
            .build()
    }

     private fun onePlayList(
         qoran: Qoran
     ) : Playlist{

         val formattedSurahNumber = String.format("%03d", qoran.surahNumber)
         val formattedAyahNumber = String.format("%03d", qoran.ayahNumber)


         return Playlist.Builder()
             .append(
                 MusicItem.Builder()
                     .setTitle("${qoran.ayahNumber} - ${qoran.surahNumber}")
                     .setArtist("")
                     .setUri("https://everyayah.com/data/AbdulSamad_64kbps_QuranExplorer.Com/$formattedSurahNumber$formattedAyahNumber.mp3")
                     .autoDuration()
                     .setIconUri("https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Abdelbasset-abdessamad-27.jpg/640px-Abdelbasset-abdessamad-27.jpg")
                     .build()
             )
             .build()
     }

    sealed class ReadScreenEvent{
        data class playAyah(val qoran : Qoran) : ReadScreenEvent()
        data class playAllAyah(val qoranList : List<Qoran>) : ReadScreenEvent()
        data object PauseAyah : ReadScreenEvent()
        data object StopAyah : ReadScreenEvent()
        data object NextAyah : ReadScreenEvent()
        data object PrevAyah : ReadScreenEvent()
    }

    enum class PlayerMode {
        IS_PLAYING,
        NOT_PLAYING,
        PAUSED,
        SINGLE_ONCE
    }
}