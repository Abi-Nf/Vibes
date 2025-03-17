package com.vibes.rv.ui.screen.player

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Transition
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Pause
import com.composables.icons.lucide.Play
import com.composables.icons.lucide.StepBack
import com.composables.icons.lucide.StepForward
import com.vibes.rv.ui.component.Icon
import com.vibes.rv.ui.layout.vibe_layout.VibeBarState
import com.vibes.rv.ui.provider.AppContext
import com.vibes.rv.ui.state.MusicState
import com.vibes.rv.util.player.playNext
import com.vibes.rv.util.player.playPrev
import com.vibes.rv.util.player.togglePlayPause

@Composable
internal fun BoxScope.CompactPlayer(
    musicState: MusicState,
    transition: Transition<VibeBarState>,
    height: Float,
) {
    val palette = AppContext.palette

    Row(
        Modifier
            .alpha(accelerationRevert(height))
            .align(Alignment.CenterEnd)
            .padding(start = 58.dp, end = 8.dp),
        Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
        Alignment.CenterVertically
    ) {
        Column(
            Modifier.weight(1f)
        ) {
            Text(
                musicState.currentMedia?.mediaMetadata?.title.toString(),
                Modifier.basicMarquee(),
                MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                musicState.currentMedia?.mediaMetadata?.artist.toString(),
                Modifier.basicMarquee(),
                palette.text.one,
                style = MaterialTheme.typography.bodySmall
            )
        }

        transition.AnimatedVisibility({ it != VibeBarState.MINIFIED }) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val player = AppContext.player

                Button({ player?.playPrev() }, musicState.hasPrev) {
                    Icon(Lucide.StepBack, MaterialTheme.colorScheme.surfaceTint)
                }

                Button({ player?.togglePlayPause() }) {
                    Icon(
                        if(musicState.isPlaying) Lucide.Pause else Lucide.Play,
                        MaterialTheme.colorScheme.inverseSurface
                    )
                }

                Button({ player?.playNext() }, musicState.hasNext) {
                    Icon(Lucide.StepForward, MaterialTheme.colorScheme.surfaceTint)
                }
            }
        }
    }
}

@Composable
private fun Button(
    onClick: () -> Unit,
    show: Boolean = true,
    content: @Composable () -> Unit
) {
    if(show) {
        Box(
            Modifier
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = onClick
                )
                .padding(4.dp)
        ) {
            content()
        }
    }
}