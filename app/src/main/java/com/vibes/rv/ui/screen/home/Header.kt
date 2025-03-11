package com.vibes.rv.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Bell
import com.composables.icons.lucide.Download
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Search
import com.composables.icons.lucide.UserRound
import com.vibes.rv.ui.component.Icon
import com.vibes.rv.ui.provider.AppContext

@Composable
internal fun Header() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp, 6.dp),
        Arrangement.SpaceBetween,
        Alignment.CenterVertically
    ) {
        UserButton()

        Row(
            Modifier.padding(horizontal = 4.dp),
            Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            Button(Lucide.Download)
            Button(Lucide.Bell)
            Button(Lucide.Search)
        }
    }
}

@Composable
private fun UserButton() {
    val palette = AppContext.palette

    Box(
        Modifier
            .size(46.dp)
            .clip(CircleShape)
            .background(palette.surface.one)
    ) {
        Icon(
            Lucide.UserRound,
            palette.text.two,
            Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun Button(
    icon: ImageVector
) {
    val palette = AppContext.palette

    Box(
        Modifier.padding(6.dp)
    ) {
        Icon(
            icon,
            palette.text.two
        )
    }
}