package com.vibes.rv.ui.screen.player

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
private fun Transition<Boolean>.TransitionBox(modifier: Modifier) {
    AnimatedVisibility({ it }) {
        Box(modifier)
    }
}

@Composable
internal fun ColumnScope.AnimatedSafeArea(heightPercent: Float, content: @Composable () -> Unit) {
    val transition = updateTransition(heightPercent > 0.3f, "player-content-transition")

    transition
        .TransitionBox(
            Modifier
                .statusBarsPadding()
                .padding(top = 4.dp)
        )

    content()

    transition.TransitionBox(
        Modifier
            .navigationBarsPadding()
            .padding(bottom = 4.dp)
    )
}