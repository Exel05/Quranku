package com.xellagon.quranku.service.location

import android.location.Location


sealed class LocationTrackerConditions<T> {
    class NoGps<Nothing> : LocationTrackerConditions<Nothing>()
    class MissingPermission<Nothing> : LocationTrackerConditions<Nothing>()
    data class Success<T>(val location : Location?) : LocationTrackerConditions<T>()
    class Error<T> : LocationTrackerConditions<T>()
}
