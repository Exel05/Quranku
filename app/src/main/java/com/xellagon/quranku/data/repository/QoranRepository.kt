package com.xellagon.quranku.data.repository

import com.xellagon.quranku.data.source.local.entity.Bookmark
import com.xellagon.quranku.data.source.local.entity.Jozz
import com.xellagon.quranku.data.source.local.entity.Page
import com.xellagon.quranku.data.source.local.entity.Qoran
import com.xellagon.quranku.data.source.local.entity.Surah
import com.xellagon.quranku.data.source.remote.model.AdzanScheduleResponse
import kotlinx.coroutines.flow.Flow

interface QoranRepository {

    fun getQuranIndexBySurah() : Flow<List<Surah>>

    fun getQuranIndexByJuz() : Flow<List<Jozz>>

    fun getQuranIndexByPage() : Flow<List<Page>>

    fun readAyahBySurahNumber(soraNumber : Int) : Flow<List<Qoran>>

    fun readAyahByJuzNumber(juzNumber : Int) : Flow<List<Qoran>>

    fun readAyahByPageNumber(pageNumber : Int) : Flow<List<Qoran>>

    fun getBookmarkList(): Flow<List<Bookmark>>

    suspend fun insertBookmark(bookmark: Bookmark)

    suspend fun deleteBookmark(bookmark: Bookmark)

    suspend fun deleteAllBookmark()

    fun searchSurah(surahName : String): Flow<List<Qoran>>

    fun seacrhAyah(ayahSurah : String) : Flow<List<Qoran>>

    suspend fun getAdzanSchedule(latitude : String, longitude : String): AdzanScheduleResponse

}