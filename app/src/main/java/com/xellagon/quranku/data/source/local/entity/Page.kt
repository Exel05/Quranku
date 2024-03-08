package com.xellagon.quranku.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.PrimaryKey

@DatabaseView("SELECT MIN(id) as id, page, sora, aya_no, sora_name_en, sora_name_ar FROM quran GROUP BY page ORDER BY id")
data class Page(
    @PrimaryKey val id : Int? = 0,
    @ColumnInfo(name = "page") val pageNumber : Int? = 0,
    @ColumnInfo(name = "sora") val surahNumber : Int? = 0,
    @ColumnInfo(name = "aya_no") val ayahNumber : Int? = 0,
    @ColumnInfo(name = "sora_name_en") val surahNameEn : String? = "",
    @ColumnInfo(name = "sora_name_ar") val surahNameAr : String? = "",
)
