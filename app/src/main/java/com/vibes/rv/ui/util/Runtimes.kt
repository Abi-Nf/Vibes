package com.vibes.rv.ui.util

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import com.vibes.rv.data.preference.ThemeMode
import com.vibes.rv.data.preference.rememberUserThemeMode

@Composable
fun <T, R> rememberAndMap(value: T, mapper: (T) -> R): R {
    return remember(value) { mapper(value) }
}

@Composable
fun isUserAppDark(): Boolean {
    val theme by rememberUserThemeMode()
    return when(theme) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }
}
