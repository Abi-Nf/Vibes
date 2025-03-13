package com.vibes.rv.ui.component.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
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

@Composable
fun ColumnScope.PlayerControl() {
    val palette = AppContext.palette

    Row(
        Modifier.align(Alignment.CenterHorizontally),
        Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
        Alignment.CenterVertically
    ) {
        SideButton {
            Icon(
                Lucide.Repeat,
                palette.text.one,
                Modifier.size(26.dp)
            )
        }

        StepButton {
            Icon(
                Lucide.StepBack,
                palette.lavender,
                Modifier.size(30.dp)
            )
        }

        Box(
            Modifier
                .clip(CircleShape)
                .background(palette.surface.one)
                .padding(16.dp)
        ) {
            Icon(
                Icons.Rounded.PlayArrow,
                palette.roseWater,
                Modifier.size(54.dp)
            )
        }

        StepButton {
            Icon(
                Lucide.StepForward,
                palette.lavender,
                Modifier.size(30.dp)
            )
        }

        SideButton {
            Icon(
                Lucide.Shuffle,
                palette.text.one,
                Modifier.size(26.dp)
            )
        }
    }
}

@Composable
private fun StepButton(
    content: @Composable () -> Unit
) {
    Box(
        Modifier
            .clip(RoundedCornerShape(22.dp))
            .background(AppContext.palette.surface.one)
            .padding(14.dp)
    ) {
        content()
    }
}

@Composable
private fun SideButton(
    content: @Composable () -> Unit
) {
    Box(
        Modifier
            .padding(8.dp)
    ) {
        content()
    }
}