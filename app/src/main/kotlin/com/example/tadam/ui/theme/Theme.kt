package com.example.tadam.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Indigo40,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    primaryContainer = Indigo80,
    onPrimaryContainer = androidx.compose.ui.graphics.Color(0xFF1A237E),
    secondary = Teal40,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    secondaryContainer = Teal80,
    onSecondaryContainer = androidx.compose.ui.graphics.Color(0xFF004D40),
    tertiary = Amber40,
    onTertiary = androidx.compose.ui.graphics.Color.White,
    background = BackgroundLight,
    onBackground = androidx.compose.ui.graphics.Color(0xFF17212B),
    surface = SurfaceLight,
    onSurface = androidx.compose.ui.graphics.Color(0xFF17212B),
)

private val DarkColorScheme = darkColorScheme(
    primary = Indigo80,
    onPrimary = androidx.compose.ui.graphics.Color(0xFF1A237E),
    primaryContainer = Indigo40,
    onPrimaryContainer = androidx.compose.ui.graphics.Color.White,
    secondary = Teal80,
    onSecondary = androidx.compose.ui.graphics.Color(0xFF004D40),
    secondaryContainer = Teal40,
    onSecondaryContainer = androidx.compose.ui.graphics.Color.White,
    tertiary = Amber80,
    onTertiary = androidx.compose.ui.graphics.Color(0xFF4E342E),
    background = BackgroundDark,
    onBackground = androidx.compose.ui.graphics.Color(0xFFE6EDF5),
    surface = SurfaceDark,
    onSurface = androidx.compose.ui.graphics.Color(0xFFE6EDF5),
)

@Composable
fun TadamTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = androidx.compose.material3.Typography(),
        content = content,
    )
}
