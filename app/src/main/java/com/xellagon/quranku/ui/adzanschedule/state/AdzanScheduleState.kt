package com.xellagon.quranku.ui.adzanschedule.state

import com.xellagon.quranku.data.source.remote.model.Time

sealed class AdzanScheduleState {
    data class Success(val data : Time) : AdzanScheduleState()
    data class Error(val error : ErrorType) : AdzanScheduleState()
    object Idle : AdzanScheduleState()

}

enum class ErrorType {
    NO_GPS,
    PERMISSION_ERROR,
    OTHERS
}