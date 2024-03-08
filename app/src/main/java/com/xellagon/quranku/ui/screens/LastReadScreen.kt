package com.xellagon.quranku.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.xellagon.quranku.R
import com.xellagon.quranku.data.source.local.LastRead
import com.xellagon.quranku.ui.screens.destinations.DetailScreenDestination
import com.xellagon.quranku.ui.screens.detail.ReadArguments
import com.xellagon.quranku.ui.theme.fontArab

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun LastReadScreen(
    navigator: DestinationsNavigator
) {
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
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color.Black)
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
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Last Read",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize(),
                    colors = CardDefaults.cardColors(Color(0xFF63574B)),
                    onClick = {
                        navigator.navigate(DetailScreenDestination(
                            ReadArguments(
                                readType = 0,
                                surahNumber = LastRead.surahNumber,
                                pageNumber = null,
                                juzNumber = null,
                                position = LastRead.position
                            )
                        ))
                    }
                ) {
                    Column(
                        Modifier
                        .fillMaxSize()
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "${LastRead.surahName} : ${LastRead.ayahSurah}",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(24.dp)
                        )
                        Text(
                            text = LastRead.ayahText,
                            color = Color(0xffBF8E0F),
                            fontSize = 25.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            textAlign = TextAlign.End
                        )
                        Text(
                            text = LastRead.trasnlate,
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                        )

                    }
                }
            }
        }
    }
}