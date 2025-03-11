package com.vibes.rv.ui.provider

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.vibes.rv.ui.theme.CatppuccinColor
import com.vibes.rv.ui.theme.Typo
import com.vibes.rv.ui.util.rememberAndMap

object AppContext {
    val palette @Composable get() = LocalPalette.current
}

@Composable
fun ProvideAppContext(
    content: @Composable () -> Unit
) {
    val palette = rememberAndMap(isSystemInDarkTheme()) { CatppuccinColor.getPalette(it) }

    CompositionLocalProvider(
        LocalPalette provides palette
    ) {
        MaterialTheme(
            typography = Typo
        ) { content() }
    }
}
