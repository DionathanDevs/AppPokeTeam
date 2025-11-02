package com.example.apppoketeam.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Esquema de cores para o Tema Escuro
private val DarkColorScheme = darkColorScheme(
  primary = PokemonRedDark,
  secondary = PokemonBlueDark,
  tertiary = PokemonYellowDark,
  background = BackgroundDark,
  surface = Color(0xFF1E1E1E), // Um pouco mais claro que o fundo
  onPrimary = Color.Black,
  onSecondary = Color.Black,
  onTertiary = Color.Black,
  onBackground = Color.White,
  onSurface = Color.White
)

// Esquema de cores para o Tema Claro
private val LightColorScheme = lightColorScheme(
  primary = PokemonRed,
  secondary = PokemonBlue,
  tertiary = PokemonYellow,
  background = BackgroundLight,
  surface = Color.White,
  onPrimary = Color.White,
  onSecondary = Color.White,
  onTertiary = Color.Black,
  onBackground = Color.Black,
  onSurface = Color.Black
)

@Composable
fun AppPokeTeamTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Desativamos a cor dinâmica para forçar nosso tema Pokémon
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit
) {
  val colorScheme = when {
    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
      val context = LocalContext.current
      if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    }
    darkTheme -> DarkColorScheme
    else -> LightColorScheme
  }
  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as Activity).window
      window.statusBarColor = colorScheme.primary.toArgb()
      WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
    }
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
