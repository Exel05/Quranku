package com.xellagon.quranku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import com.xellagon.quranku.data.source.local.entity.Surah
import com.xellagon.quranku.ui.screens.NavGraphs
import com.xellagon.quranku.ui.screens.QurankuApp
import com.xellagon.quranku.ui.screens.detail.GlobalViewModel
import com.xellagon.quranku.ui.screens.home.HomeScreen
import com.xellagon.quranku.ui.theme.QurankuTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QurankuTheme {
                QurankuApp()
            }
        }
    }
}

data class TabItem(
    val title : String,
    val layout : @Composable (list: List<Any>) -> Unit
)