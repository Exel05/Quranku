package com.xellagon.quranku.data.source.remote.service

import com.xellagon.quranku.data.source.remote.model.AdzanScheduleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("day")
    suspend fun getAdzanSchedule(
        @Query("latitude") latitude:String,
        @Query("longitude") longitude:String
    ): AdzanScheduleResponse
}