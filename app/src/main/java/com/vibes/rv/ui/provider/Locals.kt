package com.vibes.rv.ui.provider

import androidx.compose.runtime.compositionLocalOf
import com.vibes.rv.VibesViewModel
import com.vibes.rv.ui.theme.CatppuccinColor

internal val LocalPalette = compositionLocalOf { CatppuccinColor.MACHIATTO }
internal val LocalVibesModel = compositionLocalOf<VibesViewModel?> { null }
