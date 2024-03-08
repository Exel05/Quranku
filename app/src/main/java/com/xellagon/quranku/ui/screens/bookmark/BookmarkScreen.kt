package com.xellagon.quranku.ui.screens.bookmark

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.xellagon.quranku.ui.screens.destinations.DetailScreenDestination
import com.xellagon.quranku.ui.screens.detail.ReadArguments

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun BookmarkScreen(
    viewModel: BookmarkViewModel = hiltViewModel(),
    onClick: (surahNumber : Int, position : Int) -> Unit

    ) {

    val bookmarkList = viewModel.bookmarkState.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    Scaffold(
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.Black)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn() {
                items(bookmarkList) { bookmark ->

                    BookmarkItem(
                        surah = bookmark.surahName,
                        ayat = bookmark.ayahNumber,
                        arab = bookmark.ayahText,
                        delete = {
                            viewModel.deleteBookmark(
                                bookmark

                            )
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                        },
                        onClick = {
                            onClick(
                                bookmark.surahNumber, bookmark.position
                            )
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkItem(surah: String, ayat: Int, arab: String, delete: () -> Unit, onClick : ()-> Unit) {
    OutlinedCard(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.outlinedCardColors(Color(0xFF868379)),
        border = BorderStroke(1.dp, Color.White),
        onClick = {
            onClick()
        }
    ) {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(Modifier, verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = "${surah} : ${ayat}",
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                IconButton(onClick = {
                    delete()
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))

            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp), horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${arab}",
                    color = Color(0xFFFFCC48),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

            }
        }


    }
}
