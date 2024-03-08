package com.xellagon.quranku.ui.screens.onboarding

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.xellagon.quranku.R
import com.xellagon.quranku.data.source.local.GlobalPreferences
import com.xellagon.quranku.data.source.local.SettingsPreferences
import com.xellagon.quranku.di.GlobalState
import com.xellagon.quranku.ui.screens.destinations.FirstScreenDestination
import com.xellagon.quranku.ui.screens.destinations.HomeScreenDestination


@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun OnBoardingScreen(
    navigator: DestinationsNavigator
) {

    val langState by remember {
        mutableStateOf(SettingsPreferences.currentLanguage)
    }

    val contentList = listOf(
        OnBoardingContent(
            image = R.drawable.logoquran,
            title = "Selamat Datang Di Quranku",
            subTitle = "Silahkan slide -> untuk ke halaman Berikutnya"
        ),
        OnBoardingContent(
            image = R.drawable.ss2,
            title = "Di Tampilan Awal Akan Diberi Pilihan Untuk Mengakses Beberapa Screen Quranku" ,
            subTitle = "Silahkan slide -> untuk ke halaman Berikutnya"
        ),
        OnBoardingContent(
            image = R.drawable.ss1,
            title = "Terdapat Fitur Ganti Qori dan Ganti Bahasa" ,
            subTitle = "Silahkan slide -> untuk ke halaman Berikutnya"

        ),
        OnBoardingContent(
            image = R.drawable.baseline_menu_book_24,
            title = "Mari Mulai Halaman Baru dan Selamat Membaca",
            subTitle = ""
        )
    )

    val pagerState = rememberPagerState {
        contentList.size
    }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HorizontalPager(
            state = pagerState,
            Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) { currentPage ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(26.dp)
                    .background(Color.Black),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = contentList[currentPage].image),
                    contentDescription = "",
                    modifier = Modifier.size(150.dp)
                )
                Spacer(modifier = Modifier.height(34.dp))
                Text(
                    text = contentList[currentPage].title,
                    textAlign = TextAlign.Center,
                    fontSize = 28.sp,
                    lineHeight = 35.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = contentList[currentPage].subTitle,
                    Modifier.padding(top = 35.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = Color.White )
                when (currentPage) {

                    3 -> {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.White
                            ),
                            onClick = {
                                navigator.navigateUp()
                                GlobalState.isOnBoarding = false
                                SettingsPreferences.isOnBoarding = false
                            }
                        ) {
                            Text(text = "Mulai" , color = Color.Black)
                        }
                    }
                }
            }
        }
    }
}

data class OnBoardingContent(
    val image: Int,
    val title: String,
    val subTitle: String
)