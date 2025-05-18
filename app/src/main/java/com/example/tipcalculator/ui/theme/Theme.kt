package com.example.tipcalculator.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme // New import for M3
import androidx.compose.material3.lightColorScheme // New import for M3
import androidx.compose.material3.darkColorScheme // New import for M3
import androidx.compose.material3.dynamicDarkColorScheme // For dynamic color (Android 12+)
import androidx.compose.material3.dynamicLightColorScheme // For dynamic color (Android 12+)
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Define your M3 color palette
// These are just examples, customize them to your app's branding
// You can generate a full M3 palette using the Material Theme Builder: https://m3.material.io/theme-builder

private val Pink80 = Color(0xFFEFB8C8)
private val Pink40 = Color(0xFF7D5260)

private val LightAppColorScheme = lightColorScheme(
    primary = Pink40,
    onPrimary = Color.White,
    primaryContainer = Pink80, // Often a lighter/toned version of primary
    onPrimaryContainer = Pink40,
    secondary = Color(0xFF755B06), // Example secondary
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFDEA7),
    onSecondaryContainer = Color(0xFF241A00),
    tertiary = Color(0xFF006970), // Example tertiary
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFF7CF0FA),
    onTertiaryContainer = Color(0xFF002022),
    error = Color(0xFFBA1A1A),
    onError = Color.White,
    background = Color(0xFFFFFBFF),
    onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFFFFBFF),
    onSurface = Color(0xFF1C1B1F),
    // Add other colors as needed: surfaceVariant, outline, etc.
)

private val DarkAppColorScheme = darkColorScheme(
    primary = Pink80,
    onPrimary = Pink40,
    primaryContainer = Pink40, // Often a darker/toned version of primary for dark theme
    onPrimaryContainer = Pink80,
    secondary = Color(0xFFE9C27A), // Example secondary for dark
    onSecondary = Color(0xFF3F2E00),
    secondaryContainer = Color(0xFF5A4300),
    onSecondaryContainer = Color(0xFFFFDEA7),
    tertiary = Color(0xFF5DD7E0), // Example tertiary for dark
    onTertiary = Color(0xFF00373A),
    tertiaryContainer = Color(0xFF004F54),
    onTertiaryContainer = Color(0xFF7CF0FA),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    background = Color(0xFF1C1B1F),
    onBackground = Color(0xFFE6E1E5),
    surface = Color(0xFF1C1B1F),
    onSurface = Color(0xFFE6E1E5),
    // Add other colors as needed
)

@Composable
fun TipCalculatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    // We'll disable it here for simplicity in the tip calculator,
    // but you can enable it if you wish.
    dynamicColor: Boolean = false, // Set to true to enable dynamic color on API 31+
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkAppColorScheme
        else -> LightAppColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme, // Use colorScheme for M3
        typography = Typography,   // Ensure Typography is M3 compatible (or use default)
        shapes = Shapes,           // Ensure Shapes is M3 compatible (or use default)
        content = content
    )
}