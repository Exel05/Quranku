package com.xellagon.quranku.service.location.adzanViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xellagon.quranku.data.repository.QoranRepository
import com.xellagon.quranku.service.location.LocationClient
import com.xellagon.quranku.service.location.LocationTrackerConditions
import com.xellagon.quranku.ui.adzanschedule.state.AdzanScheduleState
import com.xellagon.quranku.ui.adzanschedule.state.ErrorType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdzanViewModel @Inject constructor(
    private val repository: QoranRepository,
    private val locationClient : LocationClient
) : ViewModel() {

    private val _adzanScheduleState : MutableStateFlow<AdzanScheduleState> =
        MutableStateFlow(AdzanScheduleState.Idle)
    val adzanScheduleState = _adzanScheduleState.asStateFlow()

    private val _currentLocation : MutableStateFlow<CurrentLocation> =
        MutableStateFlow(CurrentLocation(0.0,0.0))
    val currentLocation = _currentLocation.asStateFlow()

    fun getLocationUpdates() {
        viewModelScope.launch {
            _adzanScheduleState.emit(AdzanScheduleState.Idle)
            locationClient.getLocationUpdates()
                .onEach {
                    Log.d("LOKASI", it.toString())
                    when(it) {
                        is LocationTrackerConditions.Error -> {
                            _adzanScheduleState.emit(AdzanScheduleState.Error(ErrorType.OTHERS))
                        }
                        is LocationTrackerConditions.MissingPermission -> {
                            _adzanScheduleState.emit(AdzanScheduleState.Error(ErrorType.PERMISSION_ERROR))
                        }
                        is LocationTrackerConditions.NoGps -> {
                            _adzanScheduleState.emit(AdzanScheduleState.Error(ErrorType.NO_GPS))
                        }
                        is LocationTrackerConditions.Success -> {
                            val latitude = it.location?.latitude
                            val longitude = it.location?.longitude
                            if (latitude == null || longitude == null) {
                                _adzanScheduleState.emit(AdzanScheduleState.Error(ErrorType.OTHERS))
                                return@onEach
                            }
                            _currentLocation.emit(CurrentLocation(longitude, latitude))
                            val responseResult = repository.getAdzanSchedule(
                                latitude.toString(),
                                longitude.toString()
                            )
                            _adzanScheduleState.emit(AdzanScheduleState.Success(responseResult.times[0]))
                            cancel()
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

    data class CurrentLocation(
        val longitude : Double,
        val latitude : Double
    )
}