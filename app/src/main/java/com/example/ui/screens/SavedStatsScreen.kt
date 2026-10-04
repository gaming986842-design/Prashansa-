package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.db.QuizScoreEntity
import com.example.data.db.SavedWordEntity
import com.example.data.model.WordItem
import com.example.ui.components.BanglaWordCard
import com.example.ui.theme.ShivaDeepBrown
import com.example.ui.theme.ShivaGold
import com.example.ui.theme.ShivaOchre
import com.example.ui.theme.ShivaSand
import com.example.ui.theme.ShivaTerracotta
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarmBackground
import com.example.ui.theme.WarmCardBorder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SavedStatsScreen(
    allWords: List<WordItem>,
    savedMap: Map<String, SavedWordEntity>,
    quizScores: List<QuizScoreEntity>,
    learnedCount: Int,
    favoriteCount: Int,
    onSpeak: (String, String) -> Unit,
    onToggleFavorite: (String) -> Unit,
    onToggleLearned: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val favoriteWords = allWords.filter { savedMap[it.id]?.isFavorite == true }
    val learnedWords = allWords.filter { savedMap[it.id]?.isLearned == true }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(WarmBackground)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "সাফল্য ও অগ্রগতি · Learning Progress",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = ShivaDeepBrown
                )
                Text(
                    text = "Your personal database of mastered words & quiz records",
                    fontSize = 13.sp,
                    color = ShivaOchre
                )
            }
        }

        // Stats Counters Grid
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(
                    title = "Words Learned",
                    count = learnedCount.toString(),
                    icon = "✅",
                    color = SuccessGreen,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Favorites",
                    count = favoriteCount.toString(),
                    icon = "❤️",
                    color = Color(0xFFE11D48),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Quizzes Taken",
                    count = quizScores.size.toString(),
                    icon = "🏆",
                    color = ShivaGold,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Section: Bookmarked Favorites
        item {
            Text(
                text = "Bookmarked Words (${favoriteWords.size})",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = ShivaDeepBrown,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        if (favoriteWords.isEmpty()) {
            item {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarmCardBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier.padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No favorite words saved yet.\nTap the ❤️ on any card to bookmark it here!",
                            fontSize = 13.sp,
                            color = ShivaOchre,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        } else {
            items(favoriteWords, key = { it.id }) { word ->
                val saved = savedMap[word.id]
                BanglaWordCard(
                    word = word,
                    isFavorite = true,
                    isLearned = saved?.isLearned ?: false,
                    onSpeak = onSpeak,
                    onToggleFavorite = { onToggleFavorite(word.id) },
                    onToggleLearned = { onToggleLearned(word.id) }
                )
            }
        }

        // Section: Recent Quiz History
        item {
            Text(
                text = "Quiz History (${quizScores.size})",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = ShivaDeepBrown,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        if (quizScores.isEmpty()) {
            item {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarmCardBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier.padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Take your first Quiz to record scores here!",
                            fontSize = 13.sp,
                            color = ShivaOchre
                        )
                    }
                }
            }
        } else {
            items(quizScores, key = { it.id }) { scoreItem ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, WarmCardBorder, RoundedCornerShape(16.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Score: ${scoreItem.score} / ${scoreItem.totalQuestions} (${scoreItem.percentage}%)",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = ShivaTerracotta
                            )
                            val dateStr = SimpleDateFormat("MMM d, yyyy · hh:mm a", Locale.getDefault())
                                .format(Date(scoreItem.timestamp))
                            Text(
                                text = dateStr,
                                fontSize = 12.sp,
                                color = ShivaOchre
                            )
                        }

                        Text(
                            text = if (scoreItem.percentage >= 80) "🌟 Excellent" else if (scoreItem.percentage >= 50) "👍 Good" else "📖 Keep Learning",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = ShivaDeepBrown
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    count: String,
    icon: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.border(1.dp, WarmCardBorder, RoundedCornerShape(18.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = icon, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = count,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = ShivaDeepBrown,
                maxLines = 1
            )
        }
    }
}
