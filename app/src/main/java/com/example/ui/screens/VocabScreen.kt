package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.db.SavedWordEntity
import com.example.data.model.BlessingQuote
import com.example.data.model.WordItem
import com.example.ui.components.BanglaWordCard
import com.example.ui.components.ShivaBlessingBanner
import com.example.ui.theme.ShivaDeepBrown
import com.example.ui.theme.ShivaOchre
import com.example.ui.theme.ShivaSand
import com.example.ui.theme.ShivaTerracotta
import com.example.ui.theme.ShivaTextDark
import com.example.ui.theme.WarmBackground
import com.example.ui.theme.WarmCardBorder
import com.example.ui.theme.WarmPillBg

@Composable
fun VocabScreen(
    words: List<WordItem>,
    categories: List<String>,
    selectedCategory: String,
    searchQuery: String,
    blessing: BlessingQuote,
    savedMap: Map<String, SavedWordEntity>,
    onSelectCategory: (String) -> Unit,
    onSearchChange: (String) -> Unit,
    onSpeak: (String, String) -> Unit,
    onToggleFavorite: (String) -> Unit,
    onToggleLearned: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(WarmBackground)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Divine Blessing Quote Banner
        item {
            ShivaBlessingBanner(blessing = blessing)
        }

        // Search Bar
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchChange,
                placeholder = {
                    Text(
                        "Search Bangla, English, or phonetics...",
                        color = ShivaOchre.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = ShivaTerracotta
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onSearchChange("") }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear search",
                                tint = ShivaSand
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(28.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ShivaTerracotta,
                    unfocusedBorderColor = ShivaSand,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("vocab_search_input")
            )
        }

        // Horizontal Category Filter Pills
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { category ->
                    val isSelected = category.equals(selectedCategory, ignoreCase = true)
                    val bgColor = if (isSelected) ShivaTerracotta else WarmPillBg
                    val textColor = if (isSelected) Color.White else ShivaDeepBrown
                    val borderColor = if (isSelected) ShivaDeepBrown else ShivaSand

                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = bgColor,
                        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
                        shadowElevation = if (isSelected) 3.dp else 1.dp,
                        modifier = Modifier
                            .clickable { onSelectCategory(category) }
                            .testTag("category_pill_$category")
                    ) {
                        Text(
                            text = category,
                            color = textColor,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }

        // Word count hint
        item {
            Text(
                text = "Showing ${words.size} word${if (words.size != 1) "s" else ""}",
                style = MaterialTheme.typography.labelMedium,
                color = ShivaOchre
            )
        }

        // Words List
        if (words.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "🕉️", fontSize = 32.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No words found",
                            fontWeight = FontWeight.Bold,
                            color = ShivaDeepBrown
                        )
                        Text(
                            text = "Try adjusting your search or category filter",
                            fontSize = 13.sp,
                            color = ShivaOchre
                        )
                    }
                }
            }
        } else {
            items(words, key = { it.id }) { word ->
                val saved = savedMap[word.id]
                val isFav = saved?.isFavorite ?: false
                val isLearned = saved?.isLearned ?: false

                BanglaWordCard(
                    word = word,
                    isFavorite = isFav,
                    isLearned = isLearned,
                    onSpeak = onSpeak,
                    onToggleFavorite = { onToggleFavorite(word.id) },
                    onToggleLearned = { onToggleLearned(word.id) }
                )
            }
        }
    }
}
