package com.vibes.rv.ui.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.media3.session.MediaController
import com.shifthackz.catppuccin.compose.CatppuccinMaterial
import com.shifthackz.catppuccin.compose.CatppuccinTheme
import com.shifthackz.catppuccin.palette.Catppuccin
import com.vibes.rv.VibesViewModel
import com.vibes.rv.data.VibesDatabase
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
    val playlists @Composable get() = checkNotNull(LocalVibesModel.current).playlists
}

@Composable
fun ProvideAppContext(
    vibesViewModel: VibesViewModel,
    content: @Composable () -> Unit
) {
    val palette = rememberAndMap(isUserAppDark()) { it ->
        if (it) CatppuccinMaterial.Mocha(
            primary = Catppuccin.Mocha.Mauve
        ) else CatppuccinMaterial.Latte(
            primary = Catppuccin.Latte.Mauve
        )
    }

    CompositionLocalProvider(
        LocalVibesModel provides vibesViewModel
    ) {
        CatppuccinTheme.Palette(palette) { content() }
    }
}
