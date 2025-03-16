package com.vibes.rv.ui.provider

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.media3.session.MediaController
import com.vibes.rv.VibesViewModel
import com.vibes.rv.data.VibesDatabase
import com.vibes.rv.ui.theme.CatppuccinColor
import com.vibes.rv.ui.theme.Typo
import com.vibes.rv.ui.theme.catppuccin.CatppuccinPalette
import com.vibes.rv.ui.util.isUserAppDark
import com.vibes.rv.ui.util.rememberAndMap

object AppContext {
    val palette: CatppuccinPalette @Composable get() = LocalPalette.current
    val database: VibesDatabase @Composable get() = checkNotNull(LocalVibesModel.current).database
    val player: MediaController? @Composable get() = checkNotNull(LocalVibesModel.current).mediaController
    val playerState @Composable get() = checkNotNull(LocalVibesModel.current).musicState
    val albums @Composable get() = checkNotNull(LocalVibesModel.current).albums
    val artists @Composable get() = checkNotNull(LocalVibesModel.current).artists
    val tracks @Composable get() = checkNotNull(LocalVibesModel.current).tracks
}

@Composable
fun ProvideAppContext(
    vibesViewModel: VibesViewModel,
    content: @Composable () -> Unit
) {
    val palette = rememberAndMap(isUserAppDark()) { CatppuccinColor.getPalette(it) }

    CompositionLocalProvider(
        LocalPalette provides palette,
        LocalVibesModel provides vibesViewModel
    ) {
        MaterialTheme(
            typography = Typo
        ) { content() }
    }
}
