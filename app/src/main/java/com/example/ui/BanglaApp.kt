package com.example.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Spellcheck
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.ShivaLogo
import com.example.ui.screens.AlphabetScreen
import com.example.ui.screens.PhrasesScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.screens.SavedStatsScreen
import com.example.ui.screens.VocabScreen
import com.example.ui.theme.ShivaDeepBrown
import com.example.ui.theme.ShivaOchre
import com.example.ui.theme.ShivaSand
import com.example.ui.theme.ShivaTerracotta
import com.example.ui.theme.WarmBackground
import com.example.ui.theme.WarmCardBorder
import com.example.ui.theme.WarmPillBg
import com.example.ui.theme.WarmPillShadow

@Composable
fun BanglaApp(
    viewModel: BanglaViewModel,
    modifier: Modifier = Modifier
) {
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val savedWordsMap by viewModel.savedWordsState.collectAsStateWithLifecycle()
    val quizScores by viewModel.quizScoresState.collectAsStateWithLifecycle()
    val learnedCount by viewModel.learnedCount.collectAsStateWithLifecycle()
    val favoriteCount by viewModel.favoriteCount.collectAsStateWithLifecycle()
    val quizState by viewModel.quizState.collectAsStateWithLifecycle()

    val filteredWords = viewModel.getFilteredWords()
    val currentBlessing = viewModel.blessings.first()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = WarmBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Header: Divine Shiva Logo & Prashant Upadhyaya branding
            ShivaLogo()

            // Custom Styled Tabs matching the web button designs
            CustomTabBar(
                currentTab = currentTab,
                onTabSelect = { tab ->
                    viewModel.selectTab(tab)
                    if (tab == AppTab.QUIZ && quizState.isCompleted) {
                        viewModel.resetQuiz()
                    }
                }
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Screen Content
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Crossfade(targetState = currentTab, label = "tab_crossfade") { tab ->
                    when (tab) {
                        AppTab.VOCAB -> VocabScreen(
                            words = filteredWords,
                            categories = viewModel.allCategories,
                            selectedCategory = selectedCategory,
                            searchQuery = searchQuery,
                            blessing = currentBlessing,
                            savedMap = savedWordsMap,
                            onSelectCategory = viewModel::setCategory,
                            onSearchChange = viewModel::setSearchQuery,
                            onSpeak = viewModel::speak,
                            onToggleFavorite = viewModel::toggleFavorite,
                            onToggleLearned = viewModel::toggleLearned
                        )

                        AppTab.QUIZ -> QuizScreen(
                            quizState = quizState,
                            onSelectOption = viewModel::onSelectQuizOption,
                            onNextQuestion = viewModel::nextQuizQuestion,
                            onResetQuiz = viewModel::resetQuiz,
                            onSpeak = viewModel::speak
                        )

                        AppTab.ALPHABET -> AlphabetScreen(
                            alphabetList = viewModel.alphabet,
                            onSpeak = viewModel::speak
                        )

                        AppTab.PHRASES -> PhrasesScreen(
                            phrases = viewModel.phrases,
                            onSpeak = viewModel::speak
                        )

                        AppTab.STATS -> SavedStatsScreen(
                            allWords = viewModel.getFilteredWords(),
                            savedMap = savedWordsMap,
                            quizScores = quizScores,
                            learnedCount = learnedCount,
                            favoriteCount = favoriteCount,
                            onSpeak = viewModel::speak,
                            onToggleFavorite = viewModel::toggleFavorite,
                            onToggleLearned = viewModel::toggleLearned
                        )
                    }
                }
            }

            // Divine Footer Note
            HorizontalDivider(
                color = WarmCardBorder,
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "🕉️", fontSize = 12.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Prashant Upadhyaya · Bangla Learning App",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = ShivaOchre
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "🕉️", fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun CustomTabBar(
    currentTab: AppTab,
    onTabSelect: (AppTab) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 12.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppTab.entries.forEach { tab ->
            val isActive = (tab == currentTab)
            val icon: ImageVector = when (tab) {
                AppTab.VOCAB -> Icons.Default.Book
                AppTab.QUIZ -> Icons.Default.HelpOutline
                AppTab.ALPHABET -> Icons.Default.Spellcheck
                AppTab.PHRASES -> Icons.Default.Forum
                AppTab.STATS -> Icons.Default.Insights
            }

            // Web pill styling:
            // .tab-btn { background: #f2e3d5; box-shadow: 0 5px 0 #c7a685; color: #5e3b22; }
            // .tab-btn.active { background: #d4a373; color: white; box-shadow: 0 5px 0 #8b5e3c; }
            Surface(
                shape = RoundedCornerShape(50.dp),
                color = if (isActive) ShivaSand else WarmPillBg,
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isActive) ShivaTerracotta else WarmCardBorder
                ),
                shadowElevation = if (isActive) 5.dp else 2.dp,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .clickable { onTabSelect(tab) }
                    .testTag("tab_button_${tab.name.lowercase()}")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = tab.title,
                        tint = if (isActive) Color.White else ShivaOchre,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = tab.title,
                        fontSize = 13.sp,
                        fontWeight = if (isActive) FontWeight.Bold else FontWeight.SemiBold,
                        color = if (isActive) Color.White else ShivaDeepBrown
                    )
                }
            }
        }
    }
}
