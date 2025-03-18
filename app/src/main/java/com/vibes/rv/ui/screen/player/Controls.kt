package com.vibes.rv.ui.screen.player

import androidx.annotation.OptIn
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import com.composables.icons.lucide.Heart
import com.composables.icons.lucide.Lucide
import com.vibes.rv.ui.component.Icon
import com.vibes.rv.ui.component.player.PlayerControl
import com.vibes.rv.ui.component.player.PlayerPanel
import com.vibes.rv.ui.component.player.PlayerSlider
import com.vibes.rv.ui.state.MusicState

@OptIn(UnstableApi::class)
@Composable
internal fun ColumnScope.Controls(musicState: MusicState) {
    Column(
        Modifier
            .weight(1f)
            .padding(top = 8.dp)
            .clip(RectangleShape)
        ,
        Arrangement.SpaceBetween
    ) {
        Row(
            Modifier
                .padding(horizontal = 26.dp, vertical = 8.dp)
                .fillMaxWidth(),
            Arrangement.SpaceBetween,
            Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .weight(1f)
                    .padding(end = 20.dp)
            ) {
                Text(
                    musicState.currentMedia?.mediaMetadata?.title.toString(),
                    Modifier.basicMarquee(),
                    MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    musicState.currentMedia?.mediaMetadata?.artist.toString(),
                    Modifier.basicMarquee(),
                    MaterialTheme.colorScheme.surfaceBright,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            IconButton({

            }) {
                Icon(
                    Lucide.Heart,
                    MaterialTheme.colorScheme.inverseSurface,
                    Modifier.size(28.dp)
                )
            }
        }
        PlayerSlider(
            musicState.currentTime,
            musicState.currentMedia?.mediaMetadata?.durationMs ?: 0L
        )
        Column(
            Modifier.fillMaxWidth()
        ) {
            PlayerControl(musicState)
        }
        PlayerPanel({}, {}, {}, {}, {})
    }
}