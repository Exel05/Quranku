package com.xellagon.quranku.ui.screens.home

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccessTimeFilled
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Mosque
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.xellagon.quranku.R
import com.xellagon.quranku.data.source.local.LastRead
import com.xellagon.quranku.data.source.local.entity.Surah
import com.xellagon.quranku.ui.screens.bookmark.BookmarkScreen
import com.xellagon.quranku.ui.screens.destinations.DetailScreenDestination
import com.xellagon.quranku.ui.screens.destinations.KiblahFinderScreenDestination
import com.xellagon.quranku.ui.screens.destinations.LastReadScreenDestination
import com.xellagon.quranku.ui.screens.destinations.PrayTimeScreenDestination
import com.xellagon.quranku.ui.screens.destinations.SettingsScreenDestination
import com.xellagon.quranku.ui.screens.detail.AyatItem
import com.xellagon.quranku.ui.screens.detail.GlobalViewModel
import com.xellagon.quranku.ui.screens.detail.ReadArguments
import com.xellagon.quranku.ui.theme.fontArab
import kotlinx.coroutines.launch
import snow.player.BuildConfig

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Destination
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    globalViewModel: GlobalViewModel,
) {

    val coroutineScope = rememberCoroutineScope()
    val surahList = viewModel.surahListState.value
    val jusList = viewModel.juzListState.value
    val pageList = viewModel.pageListState.value
    var expanded by remember { mutableStateOf(false) }

    val searchSurah by viewModel.searchSurah.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val qoranList by viewModel.qoranList.collectAsState()
    val searchQoranList by viewModel.listSearchQoran.collectAsState()

    val searchAyahList by viewModel.searchAyahState.collectAsState()

    val activity = LocalContext.current as Activity
//    val url = BuildConfig

    LaunchedEffect(surahList) {
        globalViewModel.setTotalAyah(surahList)
    }

    LaunchedEffect(searchSurah) {
        viewModel.searchResult(searchSurah)
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
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "Localized description",
                            tint = Color.White
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Pray Time") },
                            onClick = { navigator.navigate(PrayTimeScreenDestination) },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.AccessTimeFilled,
                                    contentDescription = null
                                )
                            })
                        Divider()
                        DropdownMenuItem(
                            text = { Text("Kiblat") },
                            onClick = { navigator.navigate(KiblahFinderScreenDestination) },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Mosque,
                                    contentDescription = null
                                )
                            })
                        Divider()
                        DropdownMenuItem(
                            text = { Text("Settings") },
                            onClick = { navigator.navigate(SettingsScreenDestination) },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Settings,
                                    contentDescription = null
                                )
                            })
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier
            .padding(it)
            .background(Color.Black)) {
                SearchBar(
                    leadingIcon = {
                                  Icon(
                                      imageVector = Icons.Default.Search,
                                      contentDescription = "",
                                      tint = Color.White
                                  )
                    },
                    placeholder = {
                                  Text(text = "Search Surah")
                    },
                    query = searchSurah,
                    onQueryChange = {
                                    viewModel.onSearchSurahChange(it)
                    } ,
                    onSearch = {
                               viewModel.onSearchSurahChange(it)
                    } ,
                    active = isSearching ,
                    onActiveChange = {
                        viewModel.onToogleSearch()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .border(BorderStroke(1.dp, Color.White)),
                colors = SearchBarDefaults.colors(Color.Black))
                {
                    LazyColumn() {
                        items(searchQoranList){ item ->
                            SurahItem(
                                no = item.surahNumber!!,
                                surat = item.surahNameEn!!,
                                place = item.surahdescendPlace!!,
                                ayat = item.ayahNumber!!,
                                arabic = item.surahNameAr!!,
                                onClick = {
                                    navigator.navigate(
                                        DetailScreenDestination(
                                            ReadArguments(
                                                readType = 0,
                                                surahNumber = item.surahNumber,
                                                pageNumber = null,
                                                juzNumber = null,
                                                position = 0
                                            )
                                        )
                                    )
                                }
                            )
                        }
                        items(searchAyahList) { item ->
                            AyatItem(
                                arabAyah = item.ayahText ?: "",
                                translateAyah = item.translation_id ?: "",
                                onClick = { navigator.navigate(
                                    DetailScreenDestination(
                                        ReadArguments(
                                            readType = 0,
                                            surahNumber = item.surahNumber,
                                            pageNumber = null,
                                            juzNumber = null,
                                            position = item.ayahNumber!!.minus(1)
                                        )
                                    )
                                ) }
                            )
                        }
                    }
                }

            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(130.dp),
                border = BorderStroke(1.dp, Color.White),
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
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                        Spacer(modifier = Modifier.width(32.dp))
                        Column(modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                            verticalArrangement = Arrangement.Center) {
                            Text(
                                text = LastRead.surahName,
                            color = Color.White,
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Ayat ${LastRead.ayahSurah}",
                                color = Color.White,
                                fontWeight = FontWeight.Medium,
                                fontSize = 20.sp)
                        }
                        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = "", tint = Color(0xFFFFCC48))
                        Spacer(modifier = Modifier.width(32.dp))
                    }

                }
            }

            val tabItems = listOf(
                "Surah",
                "Juz",
                "Bookmark"

            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                var selectedTabIndex by remember {
                    mutableIntStateOf(0)
                }
                val pagerState = rememberPagerState {
                    tabItems.size
                }
                LaunchedEffect(pagerState.currentPage) {
                    selectedTabIndex = pagerState.currentPage
                }
                Column(modifier = Modifier.fillMaxSize()) {
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        containerColor = Color.Black,
                        indicator = {
                            TabRowDefaults.Indicator(
                                Modifier.tabIndicatorOffset(it[selectedTabIndex]),
                                color = Color(0xFFFFCC48)
                            )
                        }
                    ) {
                        tabItems.forEachIndexed { index, item ->
                            Tab(
                                selected = index == selectedTabIndex,
                                onClick = {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                },
                                text = {
                                    Text(text = item)
                                },
                                selectedContentColor = Color(0xFFFFCC48),
                                unselectedContentColor = Color(0xFFFFCC48)
                            )
                        }
                    }
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxSize()
                    ) { page ->
                        when (page) {
                            0 -> {
                                SurahScreen(surahList = surahList, onClick = { surah ->
                                    navigator.navigate(
                                        DetailScreenDestination(
                                            ReadArguments(
                                                readType = pagerState.currentPage,
                                                surahNumber = surah,
                                                position = 1
                                            )
                                        )
                                    )
                                })
                            }

                            1 -> {
                                JuzScreen(juzList = jusList, onCLick = { juz ->
                                    navigator.navigate(
                                        DetailScreenDestination(
                                            ReadArguments(
                                                readType = pagerState.currentPage,
                                                juzNumber = juz,
                                                position = 1
                                            )
                                        )
                                    )
                                })
                            }

                            2 -> {
                                BookmarkScreen(onClick = { surahNumber, position ->
                                    navigator.navigate(
                                        DetailScreenDestination(
                                            ReadArguments(
                                                readType = 0,
                                                surahNumber = surahNumber, position = position

                                            )
                                        )
                                    )
                                })
                            }
                        }

                    }
                }
            }
        }

    }
}