package com.vibes.rv.data.preference

import androidx.compose.runtime.Composable
import com.vibes.rv.data.preference.core.rememberPreference

@Composable
fun rememberUserThemeMode() = rememberPreference(
    PreferenceKeys.USER_THEME_MODE,
    ThemeMode.SYSTEM,
    ThemeMode
)
