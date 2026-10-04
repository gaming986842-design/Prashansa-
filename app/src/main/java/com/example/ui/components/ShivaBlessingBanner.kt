package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BlessingQuote
import com.example.ui.theme.ShivaDeepBrown
import com.example.ui.theme.ShivaGold
import com.example.ui.theme.ShivaOchre
import com.example.ui.theme.ShivaSand
import com.example.ui.theme.ShivaTerracotta

@Composable
fun ShivaBlessingBanner(
    blessing: BlessingQuote,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(
                1.dp,
                Brush.horizontalGradient(listOf(ShivaSand, ShivaGold, ShivaSand)),
                RoundedCornerShape(24.dp)
            )
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFFFF9F2),
                        Color(0xFFFBF2E5),
                        Color(0xFFF7E7D3)
                    )
                ),
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "🕉️", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = blessing.sacredTitle,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = ShivaTerracotta,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "🔱", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "“${blessing.mantraOrVerse}”",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif,
                color = ShivaDeepBrown,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = blessing.meaning,
                fontSize = 13.sp,
                color = ShivaOchre,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = blessing.blessingNote,
                fontSize = 11.sp,
                fontStyle = FontStyle.Italic,
                color = ShivaDeepBrown.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
    }
}
