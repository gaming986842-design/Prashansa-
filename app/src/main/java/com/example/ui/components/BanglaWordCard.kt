package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.WordItem
import com.example.ui.theme.ShivaDeepBrown
import com.example.ui.theme.ShivaOchre
import com.example.ui.theme.ShivaSand
import com.example.ui.theme.ShivaTerracotta
import com.example.ui.theme.ShivaTextDark
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarmCardBorder

@Composable
fun BanglaWordCard(
    word: WordItem,
    isFavorite: Boolean,
    isLearned: Boolean,
    onSpeak: (String, String) -> Unit,
    onToggleFavorite: () -> Unit,
    onToggleLearned: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.94f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .border(1.dp, WarmCardBorder, RoundedCornerShape(24.dp))
            .clickable { expanded = !expanded }
            .testTag("word_card_${word.id}")
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Main Content Area
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Action Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Category Badge
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFF9EEE3),
                        border = androidx.compose.foundation.BorderStroke(1.dp, ShivaSand.copy(alpha = 0.4f))
                    ) {
                        Text(
                            text = word.category,
                            style = MaterialTheme.typography.labelSmall,
                            color = ShivaOchre,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }

                    // Action buttons
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = onToggleLearned,
                            modifier = Modifier
                                .size(36.dp)
                                .testTag("mark_learned_${word.id}")
                        ) {
                            Icon(
                                imageVector = if (isLearned) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle,
                                contentDescription = if (isLearned) "Marked learned" else "Mark as learned",
                                tint = if (isLearned) SuccessGreen else ShivaSand,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        IconButton(
                            onClick = onToggleFavorite,
                            modifier = Modifier
                                .size(36.dp)
                                .testTag("toggle_favorite_${word.id}")
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = if (isFavorite) "Favorited" else "Add to favorites",
                                tint = if (isFavorite) Color(0xFFE11D48) else ShivaSand,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Large Bengali Word
                Text(
                    text = word.bangla,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = ShivaTextDark,
                    textAlign = TextAlign.Center,
                    lineHeight = 38.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                // English Meaning
                Text(
                    text = word.english,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ShivaOchre,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Pronunciation pill with Audio Icon
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0xFFF9EEE3),
                    modifier = Modifier.clickable { onSpeak(word.bangla, word.pronunciation) }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.VolumeUp,
                            contentDescription = "Speak pronunciation",
                            tint = ShivaTerracotta,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = word.pronunciation,
                            fontSize = 13.sp,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Medium,
                            color = ShivaTerracotta
                        )
                    }
                }

                // Expandable details (Sentence Example & Context)
                AnimatedVisibility(
                    visible = expanded,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    ) {
                        HorizontalDivider(
                            color = WarmCardBorder,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        if (word.exampleBangla.isNotEmpty()) {
                            Text(
                                text = "Example in sentence:",
                                style = MaterialTheme.typography.labelMedium,
                                color = ShivaDeepBrown,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = word.exampleBangla,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = ShivaTextDark
                            )
                            Text(
                                text = word.exampleEnglish,
                                fontSize = 13.sp,
                                fontStyle = FontStyle.Italic,
                                color = ShivaOchre
                            )
                        }

                        if (word.funFact.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Cultural note:",
                                style = MaterialTheme.typography.labelSmall,
                                color = ShivaDeepBrown,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = word.funFact,
                                fontSize = 12.sp,
                                color = ShivaTextDark.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }

            // Bottom decorative accent bar matching user's web style
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(if (isLearned) SuccessGreen else ShivaSand)
            )
        }
    }
}
