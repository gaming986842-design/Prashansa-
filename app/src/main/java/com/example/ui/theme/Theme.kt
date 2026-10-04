package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = ShivaTerracotta,
    onPrimary = Color.White,
    primaryContainer = ShivaPillBg,
    onPrimaryContainer = ShivaDeepBrown,
    secondary = ShivaOchre,
    onSecondary = Color.White,
    secondaryContainer = WarmPillBg,
    onSecondaryContainer = ShivaDeepBrown,
    tertiary = ShivaGold,
    onTertiary = Color.White,
    background = WarmBackground,
    onBackground = ShivaTextDark,
    surface = WarmSurface,
    onSurface = ShivaTextDark,
    surfaceVariant = WarmSurfaceVariant,
    onSurfaceVariant = ShivaDeepBrown,
    outline = ShivaSand,
    outlineVariant = WarmCardBorder
)

private val DarkColorScheme = darkColorScheme(
    primary = ShivaSand,
    onPrimary = ShivaDeepBrown,
    primaryContainer = ShivaTerracotta,
    onPrimaryContainer = Color.White,
    secondary = ShivaOchre,
    onSecondary = Color.White,
    secondaryContainer = ShivaDeepBrown,
    onSecondaryContainer = ShivaSand,
    tertiary = ShivaGold,
    onTertiary = Color.Black,
    background = Color(0xFF1E1712),
    onBackground = Color(0xFFF9F3E8),
    surface = Color(0xFF2A201A),
    onSurface = Color(0xFFF9F3E8),
    surfaceVariant = Color(0xFF3B2E24),
    onSurfaceVariant = Color(0xFFE6D6C6),
    outline = ShivaSand,
    outlineVariant = Color(0xFF5E4636)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep branded divine warm theme
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
