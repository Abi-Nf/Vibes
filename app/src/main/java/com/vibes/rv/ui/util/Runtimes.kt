package com.vibes.rv.ui.util

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.get
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

@Composable
fun <T: Any> NavHostController.isActive(destination: T): State<Boolean> {
    val entry by currentBackStackEntryAsState()
    return remember { derivedStateOf { graph[destination] == entry?.destination } }
}