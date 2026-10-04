package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PhraseItem
import com.example.ui.theme.ShivaDeepBrown
import com.example.ui.theme.ShivaOchre
import com.example.ui.theme.ShivaSand
import com.example.ui.theme.ShivaTerracotta
import com.example.ui.theme.ShivaTextDark
import com.example.ui.theme.WarmBackground
import com.example.ui.theme.WarmCardBorder

@Composable
fun PhrasesScreen(
    phrases: List<PhraseItem>,
    onSpeak: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(WarmBackground)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "কথা ও সংলাপ · Everyday Phrases",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = ShivaDeepBrown
                )
                Text(
                    text = "Useful daily conversations with divine blessings",
                    fontSize = 13.sp,
                    color = ShivaOchre
                )
            }
        }

        items(phrases, key = { it.id }) { phrase ->
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, WarmCardBorder, RoundedCornerShape(20.dp))
                    .clickable { onSpeak(phrase.bangla, phrase.pronunciation) }
                    .testTag("phrase_card_${phrase.id}")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFF9EEE3),
                            modifier = Modifier.padding(bottom = 6.dp)
                        ) {
                            Text(
                                text = phrase.situation,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = ShivaOchre,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }

                        Text(
                            text = phrase.bangla,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = ShivaTextDark
                        )

                        Text(
                            text = phrase.pronunciation,
                            fontSize = 13.sp,
                            fontStyle = FontStyle.Italic,
                            color = ShivaTerracotta
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = phrase.english,
                            fontSize = 14.sp,
                            color = ShivaDeepBrown
                        )
                    }

                    IconButton(
                        onClick = { onSpeak(phrase.bangla, phrase.pronunciation) },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Speak phrase",
                            tint = ShivaTerracotta
                        )
                    }
                }
            }
        }
    }
}
