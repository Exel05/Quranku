package com.xellagon.quranku.data.source.local

import androidx.room.Dao
import androidx.room.Query
import com.xellagon.quranku.data.source.local.entity.Jozz
import com.xellagon.quranku.data.source.local.entity.Page
import com.xellagon.quranku.data.source.local.entity.Qoran
import com.xellagon.quranku.data.source.local.entity.Surah
import kotlinx.coroutines.flow.Flow

@Dao
interface QoranDao {
    @Query("SELECT * FROM Surah")
    fun getQuranIndexBySurah(): Flow<List<Surah>>

    @Query("SELECT * FROM Jozz")
    fun getQuranIndexByJuz(): Flow<List<Jozz>>

    @Query("SELECT * FROM Page")
    fun getQuranIndexByPage(): Flow<List<Page>>

    @Query("SELECT * FROM quran WHERE sora = :soraNumber")
    fun readAyahBySurahNumber(soraNumber: Int): Flow<List<Qoran>>

    @Query("SELECT * FROM quran WHERE jozz = :juz")
    fun readAyahByJuzNumber(juz: Int): Flow<List<Qoran>>

    @Query("SELECT * FROM quran WHERE page = :page")
    fun readAyahByPageNumber(page: Int): Flow<List<Qoran>>

    @Query("SELECT * FROM quran WHERE sora_name_emlaey LIKE '%' || :surahName || '%' OR sora = '%' || :surahName || '%' GROUP BY sora ")
    fun searchSurah(surahName : String): Flow<List<Qoran>>

    @Query("SELECT * FROM quran WHERE aya_text_emlaey LIKE '%' || :ayahSurah || '%' OR aya_text_emlaey LIKE :ayahSurah OR translation_en  LIKE '%' || :ayahSurah || '%'")
    fun seacrhAyah(ayahSurah : String) : Flow<List<Qoran>>
}