package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AlphabetItem
import com.example.ui.components.AlphabetCard
import com.example.ui.theme.ShivaDeepBrown
import com.example.ui.theme.ShivaOchre
import com.example.ui.theme.ShivaSand
import com.example.ui.theme.ShivaTerracotta
import com.example.ui.theme.WarmBackground
import com.example.ui.theme.WarmPillBg

@Composable
fun AlphabetScreen(
    alphabetList: List<AlphabetItem>,
    onSpeak: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showVowelsOnly by remember { mutableStateOf<Boolean?>(null) } // null means All

    val filteredList = when (showVowelsOnly) {
        true -> alphabetList.filter { it.isVowel }
        false -> alphabetList.filter { !it.isVowel }
        null -> alphabetList
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 130.dp),
        modifier = modifier
            .fillMaxSize()
            .background(WarmBackground)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 32.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Header
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "বাংলা বর্ণমালা · Bengali Script",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = ShivaDeepBrown
                )
                Text(
                    text = "Tap any letter to hear authentic pronunciation",
                    fontSize = 13.sp,
                    color = ShivaOchre
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Filter Buttons: All, Vowels (স্বরবর্ণ), Consonants (ব্যঞ্জনবর্ণ)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    ScriptFilterChip(
                        title = "All",
                        isSelected = showVowelsOnly == null,
                        onClick = { showVowelsOnly = null }
                    )
                    ScriptFilterChip(
                        title = "Vowels (স্বরবর্ণ)",
                        isSelected = showVowelsOnly == true,
                        onClick = { showVowelsOnly = true }
                    )
                    ScriptFilterChip(
                        title = "Consonants (ব্যঞ্জনবর্ণ)",
                        isSelected = showVowelsOnly == false,
                        onClick = { showVowelsOnly = false }
                    )
                }
            }
        }

        items(filteredList, key = { it.char }) { item ->
            AlphabetCard(
                item = item,
                onSpeak = onSpeak
            )
        }
    }
}

@Composable
private fun ScriptFilterChip(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) ShivaTerracotta else WarmPillBg,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isSelected) ShivaDeepBrown else ShivaSand
        ),
        shadowElevation = if (isSelected) 3.dp else 1.dp,
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .clickable { onClick() }
            .testTag("filter_chip_$title")
    ) {
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Color.White else ShivaDeepBrown,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
        )
    }
}
