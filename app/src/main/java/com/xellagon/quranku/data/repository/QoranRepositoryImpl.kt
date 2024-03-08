package com.xellagon.quranku.data.repository

import androidx.paging.log
import com.xellagon.quranku.data.source.local.BookmarkDatabase
import com.xellagon.quranku.data.source.local.QoranDatabase
import com.xellagon.quranku.data.source.local.entity.Bookmark
import com.xellagon.quranku.data.source.local.entity.Jozz
import com.xellagon.quranku.data.source.local.entity.Page
import com.xellagon.quranku.data.source.local.entity.Qoran
import com.xellagon.quranku.data.source.local.entity.Surah
import com.xellagon.quranku.data.source.remote.model.AdzanScheduleResponse
import com.xellagon.quranku.data.source.remote.service.ApiInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QoranRepositoryImpl @Inject constructor(
    private val qoranDatabase: QoranDatabase,
    private val api : ApiInterface,
    private val bookmarkDatabase : BookmarkDatabase
) : QoranRepository {
    override fun getQuranIndexBySurah(): Flow<List<Surah>> {
        return qoranDatabase.dao().getQuranIndexBySurah()
    }

    override fun getQuranIndexByJuz(): Flow<List<Jozz>> {
        return qoranDatabase.dao().getQuranIndexByJuz()
    }

    override fun getQuranIndexByPage(): Flow<List<Page>> {
        return qoranDatabase.dao().getQuranIndexByPage()
    }

    override fun readAyahBySurahNumber(soraNumber: Int): Flow<List<Qoran>> {
        return qoranDatabase.dao().readAyahBySurahNumber(soraNumber)
    }

    override fun readAyahByJuzNumber(juz: Int): Flow<List<Qoran>> {
        return qoranDatabase.dao().readAyahByJuzNumber(juz)
    }

    override fun readAyahByPageNumber(page: Int): Flow<List<Qoran>> {
        return qoranDatabase.dao().readAyahByPageNumber(page)
    }

    override fun getBookmarkList(): Flow<List<Bookmark>> {
        return bookmarkDatabase.getBookmarkDao().getBookmarkList()
    }

    override suspend fun insertBookmark(bookmark: Bookmark) {
        return bookmarkDatabase.getBookmarkDao().insertBookmark(bookmark)
    }

    override suspend fun deleteBookmark(bookmark: Bookmark) {
        return bookmarkDatabase.getBookmarkDao().deleteBookmark(bookmark)
    }

    override suspend fun deleteAllBookmark() {
        return bookmarkDatabase.getBookmarkDao().deleteAllBookmark()
    }

    override fun searchSurah(surahName: String): Flow<List<Qoran>> {
        return qoranDatabase.dao().searchSurah(surahName)
    }

    override fun seacrhAyah(ayahSurah: String): Flow<List<Qoran>> {
        return qoranDatabase.dao().seacrhAyah(ayahSurah)
    }

    override suspend fun getAdzanSchedule(
        latitude : String,
        longitude : String
    ): AdzanScheduleResponse  {
        return api.getAdzanSchedule(latitude, longitude)
    }
}