package com.xellagon.quranku.service.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getLocationUpdates() : Flow<LocationTrackerConditions<Location?>>
}