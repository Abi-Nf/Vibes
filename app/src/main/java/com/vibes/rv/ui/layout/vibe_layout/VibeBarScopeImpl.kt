package com.vibes.rv.ui.layout.vibe_layout

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

internal data class VibeBarScopeImpl(
    private val viewModel: VibeLayoutViewModel,
    private val boxScope: BoxScope
) : VibeBarScope {
    override fun Modifier.align(alignment: Alignment): Modifier {
        return with(boxScope) { align(alignment) }
    }

    override fun Modifier.matchParentSize(): Modifier {
        return with(boxScope) { matchParentSize() }
    }

    @Composable
    override fun DisposableBarContent(compactHeight: Dp, content: @Composable (() -> Unit)) {
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect("bar-content") {
            coroutineScope.launch {
                viewModel.contentCompactHeight = compactHeight
                viewModel.content = content
            }
        }

        DisposableEffect("bar-content") {
            onDispose {
                coroutineScope.launch {
                    viewModel.contentCompactHeight = 0.dp
                    viewModel.content = null
                }
            }
        }
    }
}