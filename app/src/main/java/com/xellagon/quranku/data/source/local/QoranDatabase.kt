package com.xellagon.quranku.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xellagon.quranku.data.source.local.entity.Jozz
import com.xellagon.quranku.data.source.local.entity.Page
import com.xellagon.quranku.data.source.local.entity.Qoran
import com.xellagon.quranku.data.source.local.entity.Surah

@Database(
    entities = [Qoran::class],
    version = 1,
    views = [Surah::class, Jozz::class, Page::class]
)
abstract class QoranDatabase : RoomDatabase() {

    abstract fun dao(): QoranDao
}