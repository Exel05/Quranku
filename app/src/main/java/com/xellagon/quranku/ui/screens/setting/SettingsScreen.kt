package com.xellagon.quranku.ui.screens.setting

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.xellagon.quranku.data.source.local.SettingsPreferences

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun SettingsScreen(navigator: DestinationsNavigator) {


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Settings", color = Color.White)
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.clickable {
                            navigator.navigateUp()
                        }
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color.Black)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.Black)
        )
        {
            var expanded by remember { mutableStateOf(false) }
            var selectedText by remember {
                mutableStateOf(
                    SettingsPreferences
                        .listQori[SettingsPreferences.currentQori]
                        .qoriName
                )
            }

            var expandedLanguage by remember {
                mutableStateOf(false)
            }

            var selectedLanguage by remember {
                mutableStateOf(
                    if (SettingsPreferences.currentLanguage == SettingsPreferences.INDONESIA) "Indonesia"
                    else "English"

                )
            }

//            Row(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = "Text Size",
//                    fontSize = 24.sp,
//                    color = Color(0xFFFFCC48),
//                    fontWeight = FontWeight.Bold,
//                )
//                Spacer(modifier = Modifier.width(24.dp))
//                Slider(value = state, onValueChange = {})
//            }
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Bahasa",
                    color = Color(0xFFFFCC48),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Box(
                ) {
                    ExposedDropdownMenuBox(
                        expanded = expandedLanguage,
                        onExpandedChange = {
                            expandedLanguage = !expandedLanguage
                        },
                        modifier = Modifier.background(Color.Black)
                    ) {
                        TextField(
                            value = selectedLanguage,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedLanguage) },
                            modifier = Modifier.menuAnchor()
                        )

                        ExposedDropdownMenu(
                            expanded = expandedLanguage,
                            onDismissRequest = { expandedLanguage = false },
                            modifier = Modifier.background(Color.Black)
                        ) {
                                DropdownMenuItem(
                                    text = { Text(text = "Indonesia", color = Color.White) },
                                    onClick = {
                                        expandedLanguage = !expandedLanguage
                                        selectedLanguage = "Indonesia"
                                        SettingsPreferences.currentLanguage = SettingsPreferences.INDONESIA
                                    }
                                )

                            DropdownMenuItem(
                                text = { Text(text = "English", color = Color.White) },
                                onClick = {
                                    expandedLanguage = !expandedLanguage
                                    selectedLanguage = "English"
                                    SettingsPreferences.currentLanguage = SettingsPreferences.ENGLISH
                                }
                            )




                        }
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
            }

            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Qori",
                    color = Color(0xFFFFCC48),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Box(
                ) {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        },
                        modifier = Modifier.background(Color.Black)
                    ) {
                        TextField(
                            value = selectedText,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.menuAnchor()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.background(Color.Black)
                        ) {
                            SettingsPreferences.listQori.forEachIndexed {index, qori ->
                                DropdownMenuItem(
                                    text = { Text(text = qori.qoriName, color = Color.White) },
                                    onClick = {
                                        selectedText = qori.qoriName
                                        expanded = !expanded
                                        SettingsPreferences.currentQori = index
                                    }
                                )
                            }


                        }
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
            }


        }
    }

}