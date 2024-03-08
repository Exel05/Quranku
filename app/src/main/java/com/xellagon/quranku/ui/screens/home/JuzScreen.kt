package com.xellagon.quranku.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xellagon.quranku.R
import com.xellagon.quranku.data.source.local.entity.Jozz

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JuzScreen(
    juzList: List<Jozz>,
    onCLick: (Int) -> Unit
) {
    Scaffold(modifier = Modifier, containerColor = Color.Black) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(juzList) { juz ->
                JuzItem(no = juz.juzNumber!!, juz = juz.juzNumber!!, surat = juz.surahNameEn!!, ayat = juz.nomorAyah!!, onCLick = onCLick)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JuzItem(
    no : Int,
    juz : Int,
    surat : String,
    ayat : Int,
    onCLick : (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp),
        colors = CardDefaults.cardColors(Color.Black),
        onClick = {
            onCLick(
                no
            )
        }
    ) {
        Row(modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(16.dp))
            Box(modifier = Modifier.size(60.dp), contentAlignment = Alignment.Center){
                Image(painter = painterResource(id = R.drawable.border), contentDescription =null, modifier = Modifier.fillMaxSize())
                Text(text = "${no}", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Juz ${juz}", fontSize = 17.sp, color = Color.White,)
                Text(text = "${surat} Ayat ${ayat}", fontSize = 17.sp, color = Color(0xFFFFCC48))
            }
        }
    }
    Divider(modifier = Modifier, color = Color.DarkGray)
}

