package com.vibes.rv.ui.component.player

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lyrics
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ChevronUp
import com.composables.icons.lucide.EllipsisVertical
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.MoonStar
import com.composables.icons.lucide.Scissors
import com.vibes.rv.ui.component.Icon
import com.vibes.rv.ui.provider.AppContext

@Composable
fun ColumnScope.PlayerPanel(
    onClickCut: () -> Unit,
    onClickLyrics: () -> Unit,
    onClickPlayingQueue: () -> Unit,
    onClickTimer: () -> Unit,
    onClickMenu: () -> Unit
) {
    val palette = AppContext.palette

    Row(
        Modifier
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth(0.8f)
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surfaceTint)
            .padding(8.dp),
        Arrangement.SpaceAround,
        Alignment.CenterVertically
    ) {
        val iconColor = MaterialTheme.colorScheme.inverseSurface
        Button(onClickCut) {
            Icon(Lucide.Scissors, iconColor)
        }
        Button(onClickLyrics) {
            Icon(Icons.Outlined.Lyrics, iconColor)
        }
        Button(onClickPlayingQueue) {
            Icon(Lucide.ChevronUp, iconColor)
        }
        Button(onClickTimer) {
            Icon(Lucide.MoonStar, iconColor)
        }
        Button(onClickMenu) {
            Icon(Lucide.EllipsisVertical, iconColor)
        }
    }
}

@Composable
private fun Button(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        Modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = onClick
            )
            .padding(8.dp)
    ) {
        content()
    }
}