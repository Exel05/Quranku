package com.xellagon.quranku.ui.screens.detail

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.xellagon.quranku.R
import com.xellagon.quranku.data.source.local.LastRead
import com.xellagon.quranku.data.source.local.SettingsPreferences
import com.xellagon.quranku.data.source.local.entity.Bookmark
import com.xellagon.quranku.data.source.local.entity.Qoran
import com.xellagon.quranku.ui.components.PlayAyahControlPanel
import com.xellagon.quranku.ui.screens.detail.DetailViewModel.*
import com.xellagon.quranku.ui.theme.fontArab

@OptIn(ExperimentalMaterial3Api::class)
@Destination(navArgsDelegate = ReadArguments::class)

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    globalViewModel: GlobalViewModel

) {

    val ayahList by viewModel.ayahList.collectAsStateWithLifecycle()
    val totalAyahList = globalViewModel.getTotalAyah()
    val context = LocalContext.current
    val lazyColumnState = rememberLazyListState()
    val playMode = viewModel.playMode

    LaunchedEffect(true) {
        lazyColumnState.scrollToItem(viewModel.position)
    }

    Log.d("LIST AYAH", ayahList.toString())

    Scaffold(
        bottomBar = {
            if (playMode.value == PlayerMode.IS_PLAYING || playMode.value == PlayerMode.PAUSED) {
                PlayAyahControlPanel(
                    surahname = viewModel.qoran.value?.surahNameEn!!,
                    ayahNumber = viewModel.qoran.value?.ayahNumber!!,
                    qoriName = "Syekh",
                    onStop = {
                        viewModel.onEvent(ReadScreenEvent.StopAyah)
                    },
                    onSkipNext = {
                        viewModel.onEvent(ReadScreenEvent.NextAyah)
                    },
                    onSkipPrev = {
                        viewModel.onEvent(ReadScreenEvent.PrevAyah)
                    },
                    onPaused = {
                        viewModel.onEvent(ReadScreenEvent.PauseAyah)
                    },
                    isPaused = playMode.value == PlayerMode.PAUSED
                )
            }

        },
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
                }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color.Black)
            )
        }
    ) {

        Column(
            modifier = Modifier
                .padding(it)
                .background(Color.Black)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier, state = lazyColumnState) {
                items(ayahList.size) { index ->
                    val qoran = ayahList[index]
                    LastRead.surahNumber = qoran.surahNumber!!
                    LastRead.ayahSurah = qoran.ayahNumber!!
                    LastRead.ayahText = qoran.ayahText!!
                    LastRead.position = index
                    LastRead.surahName = qoran.surahNameEn!!
                    LastRead.trasnlate = qoran.translation_id!!
                    DetailItem(
                        arab = qoran.ayahText,
                        translate = if (SettingsPreferences.currentLanguage == SettingsPreferences.INDONESIA) qoran.translation_id
                                else qoran.translation_en!!,
                        surahName = qoran.surahNameEn!!,
                        surahTr = qoran.surahNameId!!,
                        no = qoran.ayahNumber,
                        insert = {
                            viewModel.addBookmark(
                                Bookmark(
                                    id = qoran.id,
                                    surahName = qoran.surahNameEn,
                                    ayahNumber = qoran.ayahNumber,
                                    surahNumber = qoran.surahNumber,
                                    ayahText = qoran.ayahText,
                                    position = index
                                )
                            )
                            Toast.makeText(context, "Added to Bookmark", Toast.LENGTH_SHORT).show()
                        },
                        playAyah = {
                            viewModel.onEvent(
                                ReadScreenEvent.playAyah(qoran)
                            )
                        },
                        playAllAyah = {
                            viewModel.onEvent(
                                ReadScreenEvent.playAllAyah(ayahList)
                            )
                        }
                    )
                    Text(text = "${totalAyahList[qoran.surahNumber!! - 1]}")
                }
            }
        }
    }

}

data class ReadArguments(
    val readType: Int = 0,
    val surahNumber: Int? = null,
    val pageNumber: Int? = null,
    val juzNumber: Int? = null,
    val position: Int
)

@Composable
fun DetailItem(
    arab: String,
    translate: String,
    surahName: String,
    surahTr: String,
    no: Int,
    playAyah: () -> Unit,
    insert: () -> Unit,
    playAllAyah : () -> Unit,
) {
    val context = LocalContext.current
    val copy: ClipboardManager = LocalClipboardManager.current

    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        if (no == 1) {

            OutlinedCard(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(160.dp),
                colors = CardDefaults.outlinedCardColors(Color.Gray),
                border = BorderStroke(1.dp, Color.White)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "$surahName ( $surahTr )",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    IconButton(
                        onClick = {
                                  playAllAyah()
                        },
                        colors = IconButtonDefaults.filledIconButtonColors(Color.Black)){
                        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "", tint = Color.White)
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(Color.Black)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .padding(8.dp),
                ) {
                    Text(
                        text = arab,
                        color = Color(0xFFFFCC48),
                        fontSize = 25.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
                Row(modifier = Modifier) {

                    Text(
                        text = translate,
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .weight(1f)
                    )
                    IconButton(onClick = {
                        copy.setText(AnnotatedString("$arab\n $translate"))
                        Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show()

                    }) {
                        Icon(
                            imageVector = Icons.Default.CopyAll,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = {
                        val intent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "($no) $arab")
                            type = "text/plain"
                        }

                        val shareIntent = Intent.createChooser(intent, null)
                        context.startActivity(shareIntent)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = {
                        insert()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                    IconButton(onClick = {
                        playAyah()
                    }) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }


                }
            }
            Spacer(modifier = Modifier.height(8.dp))

        }
    }

}

@Composable
fun AyatItem(
    arabAyah : String,
    translateAyah : String,
    onClick : () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.Black),
        onClick = {
            onClick()
        }
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(8.dp),
            ) {
                Text(
                    text = arabAyah,
                    color = Color(0xFFFFCC48),
                    fontSize = 25.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            }
            Row(modifier = Modifier) {

                Text(
                    text = translateAyah,
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

    }
}