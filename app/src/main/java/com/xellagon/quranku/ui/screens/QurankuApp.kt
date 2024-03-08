package com.xellagon.quranku.ui.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.navigation.dependency
import com.xellagon.quranku.data.source.local.SettingsPreferences
import com.xellagon.quranku.ui.screens.destinations.DetailScreenDestination
import com.xellagon.quranku.ui.screens.destinations.FirstScreenDestination
import com.xellagon.quranku.ui.screens.destinations.HomeScreenDestination
import com.xellagon.quranku.ui.screens.destinations.KiblahFinderScreenDestination
import com.xellagon.quranku.ui.screens.destinations.OnBoardingScreenDestination
import com.xellagon.quranku.ui.screens.destinations.PrayTimeScreenDestination
import com.xellagon.quranku.ui.screens.destinations.SettingsScreenDestination
import com.xellagon.quranku.ui.screens.detail.DetailScreen
import com.xellagon.quranku.ui.screens.detail.GlobalViewModel
import com.xellagon.quranku.ui.screens.first.FirstScreen
import com.xellagon.quranku.ui.screens.home.HomeScreen
import com.xellagon.quranku.ui.screens.onboarding.OnBoardingScreen
import com.xellagon.quranku.ui.screens.setting.KiblahFinderScreen
import com.xellagon.quranku.ui.screens.setting.PrayTimeScreen
import com.xellagon.quranku.ui.screens.setting.SettingsScreen

@Composable
fun QurankuApp(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val context = LocalContext.current as ComponentActivity
        val globalViewModel : GlobalViewModel = viewModel(context)
        DestinationsNavHost(
            navGraph = NavGraphs.root,
        ){
            composable(FirstScreenDestination){
                if (SettingsPreferences.isOnBoarding){
                    destinationsNavigator.navigate(OnBoardingScreenDestination){
                        popUpTo(OnBoardingScreenDestination.route){
                            inclusive = true
                            saveState = true
                        }
                        restoreState = false
                        launchSingleTop = true
                    }
                }
                FirstScreen(navigator = destinationsNavigator)
            }
            composable(OnBoardingScreenDestination){
                OnBoardingScreen(navigator = destinationsNavigator)
            }
            composable(HomeScreenDestination){
                HomeScreen(navigator = destinationsNavigator, globalViewModel = globalViewModel)
            }
            composable(SettingsScreenDestination) {
                SettingsScreen(navigator = destinationsNavigator)
            }
            composable(DetailScreenDestination) {
                DetailScreen(globalViewModel = globalViewModel)
            }
            composable(PrayTimeScreenDestination) {
                PrayTimeScreen(navigator = destinationsNavigator)
            }
            composable(KiblahFinderScreenDestination) {
                KiblahFinderScreen()
            }
        }
    }
}