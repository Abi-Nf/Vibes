package com.vibes.rv.ui.layout.vibe_layout

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun VibeLayout(
    content: @Composable VibeBarScope.() -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        val model = viewModel { VibeLayoutViewModel() }
        val scope = remember { VibeBarScopeImpl(model, this@Box) }
        content(scope)
        VibeBar(
            model,
            inset = BarParam(PaddingValues(12.dp), PaddingValues(vertical = 16.dp, horizontal = 20.dp)),
            playerHeight = BarParam(65.dp, 60.dp),
            width = BarParam(1f, 0.6f),
            roundness = BarParam(20.dp, 36.dp)
        )
    }
}