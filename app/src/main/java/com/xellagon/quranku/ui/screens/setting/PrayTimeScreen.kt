package com.xellagon.quranku.ui.screens.setting

import android.Manifest
import android.location.Geocoder
import android.util.Log
import android.widget.HorizontalScrollView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.xellagon.quranku.R
import com.xellagon.quranku.service.location.adzanViewModel.AdzanViewModel
import com.xellagon.quranku.ui.adzanschedule.components.AdzanItem
import com.xellagon.quranku.ui.adzanschedule.state.AdzanScheduleState
import com.xellagon.quranku.ui.adzanschedule.state.ErrorType
import com.xellagon.quranku.ui.theme.fontArab
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Destination
@Composable
fun PrayTimeScreen(
    viewModel : AdzanViewModel = hiltViewModel(),
     navigator : DestinationsNavigator
) {

    val context = LocalContext.current
    val locationPermission = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    if (!locationPermission.allPermissionsGranted) {
        LaunchedEffect(true ) {
            locationPermission.launchMultiplePermissionRequest()
        }
    }

    LaunchedEffect(locationPermission.allPermissionsGranted) {
        if (locationPermission.allPermissionsGranted) {
            viewModel.getLocationUpdates()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Quranku",
                            style = fontArab,
                            fontSize = 42.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color.Black),
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.clickable {
                            navigator.navigateUp()
                        }
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            viewModel.currentLocation.collectAsState().let {
                @Suppress("DEPRECATION") val address = Geocoder(
                    context,
                    Locale.getDefault()
                ).getFromLocation(
                    it.value.latitude,
                    it.value.longitude,
                    1
                )
                if (!address.isNullOrEmpty()) {
                    val locality = address.first().locality
                    val subLocality = address.first().subLocality
                    val subAdminArea = address.first().subAdminArea
                    val currentLocation = "$locality, $subLocality, $subAdminArea"
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .height(34.dp)
                        .background(Color.Gray),
                        verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = currentLocation, color = Color.Black, fontWeight = FontWeight.Bold)

                    }

                }
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                viewModel.adzanScheduleState.collectAsState(initial = AdzanScheduleState.Idle)
                    .let { state ->
                        Log.d("STATE", state.value.toString())
                        when(val event = state.value) {
                            is AdzanScheduleState.Error -> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                ) {
                                    Column(
                                        modifier = Modifier.align(Alignment.Center),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = when(event.error) {
                                                ErrorType.NO_GPS -> "GPS is not Activated"
                                                ErrorType.PERMISSION_ERROR -> "Please give this app permission for your location"
                                                ErrorType.OTHERS -> "Error when getting Location"
                                            },
                                            textAlign = TextAlign.Center,
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                        Spacer(modifier = Modifier.height(12.dp))
                                        Button(onClick = { viewModel.getLocationUpdates() }) {
                                            Text(text = "Retry?")
                                        }
                                    }
                                }
                            }
                            is AdzanScheduleState.Idle -> {
                                Box(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Column(
                                        modifier = Modifier.align(Alignment.Center),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "Please Turn On Location",
                                            style = MaterialTheme.typography.titleMedium,
                                            textAlign = TextAlign.Center
                                        )
                                        Spacer(modifier = Modifier.height(24.dp))
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                            is AdzanScheduleState.Success -> {
                                    val sholat = event.data
                                    AdzanItem(sholah = "Shubuh", time = sholat.fajr)
                                    AdzanItem(sholah = "Dzuhur", time = sholat.dhuhr)
                                    AdzanItem(sholah = "Ashar", time = sholat.asr)
                                    AdzanItem(sholah = "Maghrib", time = sholat.maghrib)
                                    AdzanItem(sholah = "Isya", time = sholat.isha)



                            }
                        }
                    }
            }
        }
    }
}