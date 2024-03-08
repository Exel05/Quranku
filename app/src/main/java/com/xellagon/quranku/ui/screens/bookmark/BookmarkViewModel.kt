package com.xellagon.quranku.ui.screens.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xellagon.quranku.data.repository.QoranRepository
import com.xellagon.quranku.data.source.local.entity.Bookmark
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val repository: QoranRepository
) : ViewModel() {

    val bookmarkState =
        repository.getBookmarkList().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(500),
            emptyList()
        )

    fun deleteBookmark(bookmark: Bookmark) {
        viewModelScope.launch {
            repository.deleteBookmark(bookmark)
        }
    }

    fun onEvent(event: BookmarkScreenEvent) {
        when (event) {
            is BookmarkScreenEvent.DeleteAllBookmark -> {
                viewModelScope.launch {
                    repository.deleteAllBookmark()
                }
            }

            is BookmarkScreenEvent.DeleteBookmark -> {
                viewModelScope.launch {
                    repository.deleteBookmark(event.bookmark)
                }
            }
        }
    }

}

sealed class BookmarkScreenEvent {
    data object DeleteAllBookmark : BookmarkScreenEvent()
    data class DeleteBookmark(val bookmark: Bookmark) : BookmarkScreenEvent()
}