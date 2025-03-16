package com.vibes.rv.ui.layout.vibe_layout

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

interface VibeBarScope : BoxScope {
    @Composable fun DisposableBarContent(compactHeight: Dp, content: @Composable () -> Unit)
}