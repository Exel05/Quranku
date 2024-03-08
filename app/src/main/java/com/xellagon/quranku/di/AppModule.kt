package com.xellagon.quranku.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.android.gms.location.LocationServices
import com.xellagon.quranku.R
import com.xellagon.quranku.data.repository.QoranRepository
import com.xellagon.quranku.data.repository.QoranRepositoryImpl
import com.xellagon.quranku.data.source.local.BookmarkDatabase
import com.xellagon.quranku.data.source.local.QoranDatabase
import com.xellagon.quranku.data.source.remote.service.ApiInterface
import com.xellagon.quranku.service.MyPlayerService
import com.xellagon.quranku.service.location.LocationClient
import com.xellagon.quranku.service.location.LocationClientImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import snow.player.PlayerClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocationClient(
        application : Application,
        coroutineScope: CoroutineScope
    ) : LocationClient {
        return LocationClientImpl(
            application,
            LocationServices.getFusedLocationProviderClient(application.applicationContext),
            coroutineScope
        )
    }

    @Provides
    @Singleton
    fun provideCoroutineScope(
    ) : CoroutineScope {
        return CoroutineScope(Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun provideSnowPlayer(
        @ApplicationContext context: Context
    ) : PlayerClient {
        return PlayerClient.newInstance(context, MyPlayerService::class.java)
    }

    @Provides
    @Singleton
    fun provideBookmarkDatabase(
        @ApplicationContext context: Context
    ) : BookmarkDatabase {
        return Room.databaseBuilder(
                context,
            BookmarkDatabase::class.java,
            "bookmark.db"
                ).build()
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext applicationContext: Context
    ): QoranDatabase {
        return Room.databaseBuilder(
            applicationContext,
            QoranDatabase::class.java,
            "qoran.db"
        ).createFromInputStream {
            applicationContext.resources.openRawResource(R.raw.qoran)
        }.build()

    }

    @Provides
    @Singleton
    fun provideApi(
        @ApplicationContext applicationContext: Context
    ) : ApiInterface {
        return Retrofit.Builder()
            .baseUrl("https://prayer-times-xi.vercel.app/api/prayer/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(
        database: QoranDatabase,
        api : ApiInterface,
        bookmarkDatabase: BookmarkDatabase
    ): QoranRepository {
        return QoranRepositoryImpl(database, api, bookmarkDatabase)
    }
}