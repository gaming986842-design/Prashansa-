package com.example.ui.components

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
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.data.model.AlphabetItem
import com.example.ui.theme.ShivaDeepBrown
import com.example.ui.theme.ShivaGold
import com.example.ui.theme.ShivaOchre
import com.example.ui.theme.ShivaSand
import com.example.ui.theme.ShivaTerracotta
import com.example.ui.theme.ShivaTextDark
import com.example.ui.theme.WarmCardBorder

@Composable
fun AlphabetCard(
    item: AlphabetItem,
    onSpeak: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, WarmCardBorder, RoundedCornerShape(20.dp))
            .clickable { onSpeak(item.char, item.romanization) }
            .testTag("alphabet_card_${item.char}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Character bubble
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFBF2E5))
                    .border(1.dp, ShivaSand, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.char,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = ShivaTerracotta,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Romanization
            Text(
                text = item.romanization,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = ShivaDeepBrown
            )

            // Sound Description
            Text(
                text = item.soundDescription,
                fontSize = 11.sp,
                fontStyle = FontStyle.Italic,
                color = ShivaOchre,
                textAlign = TextAlign.Center,
                lineHeight = 14.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Sample word
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFF9EEE3),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = item.exampleWord,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ShivaTerracotta
                    )
                    Text(
                        text = item.exampleMeaning,
                        fontSize = 10.sp,
                        color = ShivaDeepBrown.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}
