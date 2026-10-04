package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.QuizUiState
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.ErrorRedBg
import com.example.ui.theme.ShivaDeepBrown
import com.example.ui.theme.ShivaGold
import com.example.ui.theme.ShivaOchre
import com.example.ui.theme.ShivaSand
import com.example.ui.theme.ShivaTerracotta
import com.example.ui.theme.ShivaTextDark
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SuccessGreenBg
import com.example.ui.theme.WarmBackground
import com.example.ui.theme.WarmCardBorder
import com.example.ui.theme.WarmPillBg

@Composable
fun QuizScreen(
    quizState: QuizUiState,
    onSelectOption: (Int) -> Unit,
    onNextQuestion: () -> Unit,
    onResetQuiz: () -> Unit,
    onSpeak: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmBackground)
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (quizState.isCompleted) {
            // Completion View
            QuizCompletedCard(
                score = quizState.score,
                total = quizState.questions.size,
                onRestart = onResetQuiz
            )
        } else if (quizState.questions.isNotEmpty()) {
            val q = quizState.questions[quizState.currentIndex]
            val progress = (quizState.currentIndex + 1).toFloat() / quizState.questions.size

            // Top Status Bar (Question counter, Streak, Score)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    border = androidx.compose.foundation.BorderStroke(1.dp, ShivaSand),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(
                        text = "Question ${quizState.currentIndex + 1} / ${quizState.questions.size}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = ShivaDeepBrown,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (quizState.streak > 1) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Color(0xFFFEF3C7),
                            border = androidx.compose.foundation.BorderStroke(1.dp, ShivaGold)
                        ) {
                            Text(
                                text = "🔥 ${quizState.streak} Streak",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = ShivaTerracotta,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                    }

                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White,
                        border = androidx.compose.foundation.BorderStroke(1.dp, ShivaSand)
                    ) {
                        Text(
                            text = "Score: ${quizState.score}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = ShivaTerracotta,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            // Progress bar
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = ShivaTerracotta,
                trackColor = WarmCardBorder
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Main Quiz Box
            Card(
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFEFAF5)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, WarmCardBorder, RoundedCornerShape(32.dp))
                    .testTag("quiz_box")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Divine Om hint
                    Text(
                        text = "🕉️",
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Question Text
                    Text(
                        text = q.question,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        color = ShivaDeepBrown,
                        textAlign = TextAlign.Center,
                        lineHeight = 28.sp,
                        modifier = Modifier.testTag("quiz_question_text")
                    )

                    // If question has target Bengali word, allow pronunciation
                    if (q.targetWordBangla.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        IconButton(
                            onClick = { onSpeak(q.targetWordBangla, "") },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "Hear word",
                                tint = ShivaTerracotta
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Options List
                    q.options.forEachIndexed { idx, opt ->
                        val isSelected = (quizState.selectedIndex == idx)
                        val isCorrectOption = (idx == q.correctIndex)

                        val optionBg: Color
                        val optionBorder: Color
                        val optionTextColor: Color

                        if (!quizState.isAnswered) {
                            optionBg = Color.White
                            optionBorder = ShivaSand
                            optionTextColor = ShivaTextDark
                        } else {
                            if (isCorrectOption) {
                                optionBg = SuccessGreenBg
                                optionBorder = SuccessGreen
                                optionTextColor = SuccessGreen
                            } else if (isSelected) {
                                optionBg = ErrorRedBg
                                optionBorder = ErrorRed
                                optionTextColor = ErrorRed
                            } else {
                                optionBg = Color.White.copy(alpha = 0.6f)
                                optionBorder = WarmCardBorder
                                optionTextColor = ShivaTextDark.copy(alpha = 0.5f)
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(40.dp),
                            color = optionBg,
                            border = androidx.compose.foundation.BorderStroke(2.dp, optionBorder),
                            shadowElevation = if (!quizState.isAnswered) 2.dp else 0.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clickable(enabled = !quizState.isAnswered) {
                                    onSelectOption(idx)
                                }
                                .testTag("quiz_option_$idx")
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 18.dp, vertical = 14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = opt,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = optionTextColor
                                )

                                if (quizState.isAnswered) {
                                    if (isCorrectOption) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = "Correct",
                                            tint = SuccessGreen,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    } else if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Wrong",
                                            tint = ErrorRed,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Feedback Message
                    AnimatedVisibility(
                        visible = quizState.isAnswered,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Surface(
                                shape = RoundedCornerShape(30.dp),
                                color = if (quizState.isCorrect) SuccessGreenBg else ErrorRedBg,
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (quizState.isCorrect) SuccessGreen else ErrorRed
                                )
                            ) {
                                Text(
                                    text = if (quizState.isCorrect) "✅ Correct! সঠিক!" else "❌ Oops! Correct answer: ${q.options[q.correctIndex]}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (quizState.isCorrect) SuccessGreen else ErrorRed,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                    textAlign = TextAlign.Center
                                )
                            }

                            if (q.explanation.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = q.explanation,
                                    fontSize = 13.sp,
                                    color = ShivaDeepBrown,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Next Button
                    Button(
                        onClick = onNextQuestion,
                        enabled = quizState.isAnswered,
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ShivaTerracotta,
                            contentColor = Color.White,
                            disabledContainerColor = ShivaSand.copy(alpha = 0.5f),
                            disabledContentColor = Color.White.copy(alpha = 0.7f)
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(50.dp)
                            .testTag("quiz_next_button")
                    ) {
                        Text(
                            text = if (quizState.currentIndex == quizState.questions.size - 1) "Finish Quiz" else "Next",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Next"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuizCompletedCard(
    score: Int,
    total: Int,
    onRestart: () -> Unit
) {
    val percentage = if (total > 0) (score * 100) / total else 0
    val isPerfect = (score == total)

    Card(
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, ShivaSand, RoundedCornerShape(32.dp))
            .padding(vertical = 16.dp)
            .testTag("quiz_completed_card")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (isPerfect) "🌟" else "🎉",
                fontSize = 48.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Quiz Complete!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = ShivaDeepBrown
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Score Badge
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = Color(0xFFF9EEE3),
                border = androidx.compose.foundation.BorderStroke(1.dp, ShivaSand)
            ) {
                Text(
                    text = "Your Score: $score / $total ($percentage%)",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = ShivaTerracotta,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = if (isPerfect)
                    "🌟 Perfect! You are a Bangla star! অসাধারণ!"
                else if (percentage >= 70)
                    "👏 Great job! Your Bangla skills are growing well! খুব ভালো!"
                else
                    "🙏 Keep practicing! With divine blessings you will master it!",
                fontSize = 15.sp,
                color = ShivaOchre,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onRestart,
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ShivaTerracotta,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(50.dp)
                    .testTag("quiz_restart_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Restart Quiz"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Restart Quiz",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
