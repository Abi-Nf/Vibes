package com.vibes.rv.ui.component.player

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Repeat
import com.composables.icons.lucide.Shuffle
import com.composables.icons.lucide.StepBack
import com.composables.icons.lucide.StepForward
import com.vibes.rv.ui.component.Icon
import com.vibes.rv.ui.provider.AppContext
import com.vibes.rv.ui.state.MusicState
import com.vibes.rv.util.player.playNext
import com.vibes.rv.util.player.playPrev
import com.vibes.rv.util.player.togglePlayPause
import com.vibes.rv.util.player.toggleShuffle
import com.vibes.rv.util.player.triggerLoop

@Composable
fun ColumnScope.PlayerControl(musicState: MusicState) {
    val player = AppContext.player

    Row(
        Modifier.align(Alignment.CenterHorizontally),
        Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
        Alignment.CenterVertically
    ) {
        SideButton({ player?.triggerLoop() }) {
            Icon(
                Lucide.Repeat,
                MaterialTheme.colorScheme.inverseSurface,
                Modifier.size(26.dp)
            )
        }

        StepButton({ player?.playPrev() }) {
            Icon(
                Lucide.StepBack,
                MaterialTheme.colorScheme.secondary,
                Modifier.size(30.dp)
            )
        }

        Box(
            Modifier
                .clip(CircleShape)
                .clickable(
                    indication = null,
                    interactionSource = null
                ) { player?.togglePlayPause() }
                .background(MaterialTheme.colorScheme.surfaceTint)
                .padding(16.dp)
        ) {
            Icon(
                if (musicState.isPlaying) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
                MaterialTheme.colorScheme.tertiary,
                Modifier.size(54.dp)
            )
        }

        StepButton({ player?.playNext() }) {
            Icon(
                Lucide.StepForward,
                MaterialTheme.colorScheme.secondary,
                Modifier.size(30.dp)
            )
        }

        SideButton({ player?.toggleShuffle() }) {
            Icon(
                Lucide.Shuffle,
                MaterialTheme.colorScheme.inverseSurface,
                Modifier.size(26.dp)
            )
        }
    }
}

@Composable
private fun StepButton(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        Modifier
            .clip(RoundedCornerShape(22.dp))
            .clickable(
                indication = null,
                interactionSource = null,
                onClick = onClick
            )
            .background(MaterialTheme.colorScheme.surfaceTint)
            .padding(14.dp)
    ) {
        content()
    }
}

@Composable
private fun SideButton(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        Modifier
            .clickable(
                indication = null,
                interactionSource = null,
                onClick = onClick
            )
            .padding(8.dp)
    ) {
        content()
    }
}