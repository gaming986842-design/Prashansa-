package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GangaBlue
import com.example.ui.theme.ShivaDeepBrown
import com.example.ui.theme.ShivaGold
import com.example.ui.theme.ShivaOchre
import com.example.ui.theme.ShivaSand
import com.example.ui.theme.ShivaTerracotta

@Composable
fun ShivaLogo(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "divine_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 12.dp)
            .testTag("shiva_logo_section"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Divine circular emblem
        Box(
            modifier = Modifier
                .size(118.dp)
                .shadow(12.dp, CircleShape, spotColor = ShivaOchre.copy(alpha = 0.5f))
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFFFF7ED),
                            Color(0xFFF7E7D3),
                            Color(0xFFE6CDB1)
                        )
                    )
                )
                .border(3.dp, ShivaSand, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(92.dp)) {
                val w = size.width
                val h = size.height

                // Sacred Aura Outer ring
                drawCircle(
                    color = ShivaGold.copy(alpha = 0.25f),
                    radius = w * 0.46f,
                    style = Stroke(width = 2.dp.toPx())
                )

                // Trident / Trishul Center shaft
                drawLine(
                    color = ShivaTerracotta,
                    start = Offset(w * 0.5f, h * 0.12f),
                    end = Offset(w * 0.5f, h * 0.44f),
                    strokeWidth = 3.5.dp.toPx(),
                    cap = StrokeCap.Round
                )

                // Trishul prongs (Left and Right curves meeting in center)
                val trishulPath = Path().apply {
                    moveTo(w * 0.35f, h * 0.20f)
                    quadraticBezierTo(w * 0.38f, h * 0.32f, w * 0.5f, h * 0.32f)
                    quadraticBezierTo(w * 0.62f, h * 0.32f, w * 0.65f, h * 0.20f)
                }
                drawPath(
                    path = trishulPath,
                    color = ShivaTerracotta,
                    style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                )

                // Trishul central tip arrow
                val tipPath = Path().apply {
                    moveTo(w * 0.44f, h * 0.19f)
                    lineTo(w * 0.5f, h * 0.08f)
                    lineTo(w * 0.56f, h * 0.19f)
                }
                drawPath(
                    path = tipPath,
                    color = ShivaTerracotta,
                    style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                )

                // Crescent Moon (Chandra) on the right
                val moonPath = Path().apply {
                    moveTo(w * 0.60f, h * 0.14f)
                    quadraticBezierTo(w * 0.73f, h * 0.13f, w * 0.70f, h * 0.25f)
                    quadraticBezierTo(w * 0.64f, h * 0.20f, w * 0.60f, h * 0.14f)
                }
                drawPath(path = moonPath, color = Color(0xFFFBF4EB))
                drawPath(
                    path = moonPath,
                    color = ShivaGold,
                    style = Stroke(width = 1.2.dp.toPx())
                )

                // Damru (sacred drum) in the middle
                val damruPath = Path().apply {
                    moveTo(w * 0.42f, h * 0.46f)
                    lineTo(w * 0.58f, h * 0.46f)
                    lineTo(w * 0.42f, h * 0.58f)
                    lineTo(w * 0.58f, h * 0.58f)
                    close()
                }
                drawPath(path = damruPath, color = Color(0xFFD2A679))
                drawPath(
                    path = damruPath,
                    color = ShivaTerracotta,
                    style = Stroke(width = 1.8.dp.toPx())
                )
                drawLine(
                    color = ShivaDeepBrown,
                    start = Offset(w * 0.42f, h * 0.52f),
                    end = Offset(w * 0.58f, h * 0.52f),
                    strokeWidth = 1.5.dp.toPx()
                )

                // Holy Ganga wave stream near bottom
                val gangaPath = Path().apply {
                    moveTo(w * 0.30f, h * 0.67f)
                    quadraticBezierTo(w * 0.40f, h * 0.61f, w * 0.50f, h * 0.67f)
                    quadraticBezierTo(w * 0.60f, h * 0.73f, w * 0.70f, h * 0.67f)
                }
                drawPath(
                    path = gangaPath,
                    color = GangaBlue,
                    style = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round)
                )

                // Third eye (Trinetra)
                drawLine(
                    color = ShivaGold,
                    start = Offset(w * 0.45f, h * 0.38f),
                    end = Offset(w * 0.55f, h * 0.38f),
                    strokeWidth = 2.5.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            // Sacred Om Symbol placed near bottom
            Text(
                text = "ॐ",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = ShivaGold,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 6.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Author & Brand Plaque: "Prashant Upadhyaya · Bangla Learning"
        Surface(
            shape = RoundedCornerShape(32.dp),
            color = Color(0xFFFFF8F0),
            border = androidx.compose.foundation.BorderStroke(1.dp, ShivaSand),
            shadowElevation = 3.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Prashant Upadhyaya",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 17.sp,
                    color = ShivaTerracotta,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "🕉️",
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Bangla Learning",
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = ShivaOchre
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Header Title
        Text(
            text = "Learn Bangla",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            ),
            color = ShivaDeepBrown,
            textAlign = TextAlign.Center
        )
        Text(
            text = "with divine blessings · সহজ বাংলা",
            style = MaterialTheme.typography.bodyMedium,
            color = ShivaOchre,
            textAlign = TextAlign.Center
        )
    }
}
