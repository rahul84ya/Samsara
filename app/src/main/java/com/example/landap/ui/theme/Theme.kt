package com.example.landap.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = OrangePrimary,
    onPrimary = SurfaceLight,
    primaryContainer = OrangeContainer,
    onPrimaryContainer = OnOrangeContainer,
    secondary = SaffronGold,
    onSecondary = SurfaceLight,
    secondaryContainer = SaffronGoldContainer,
    onSecondaryContainer = OnSaffronContainer,
    tertiary = EarthBrown,
    background = BackgroundLight,
    surface = SurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurface = TextPrimary,
    onSurfaceVariant = TextSecondary,
    outline = BorderLight
)

private val DarkColorScheme = darkColorScheme(
    primary = OrangeVibrant,
    onPrimary = OrangeDark,
    primaryContainer = OrangeDark,
    onPrimaryContainer = OrangeContainer,
    secondary = SaffronGold,
    onSecondary = OrangeDark,
    secondaryContainer = OnSaffronContainer,
    onSecondaryContainer = SaffronGoldContainer,
    background = Color(0xFF1C1917),
    surface = Color(0xFF292524),
    surfaceVariant = Color(0xFF44403C),
    onSurface = Color(0xFFFAFAF9),
    onSurfaceVariant = Color(0xFFE7E5E4),
    outline = Color(0xFF78716C)
)

@Composable
fun LandAPTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                try {
                    val insetsController = WindowCompat.getInsetsController(window, view)
                    insetsController.isAppearanceLightStatusBars = false
                    insetsController.isAppearanceLightNavigationBars = false
                } catch (_: Exception) {}
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
