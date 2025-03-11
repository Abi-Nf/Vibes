package com.vibes.rv.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.HeartPulse
import com.composables.icons.lucide.History
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Origami
import com.composables.icons.lucide.TrendingUp
import com.vibes.rv.ui.component.Icon
import com.vibes.rv.ui.provider.AppContext
import com.vibes.rv.ui.util.color.interpolate

@Composable
internal fun Shortcut() {
    Row(
        Modifier.padding(12.dp, 13.dp),
        Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        Alignment.CenterVertically
    ) {
        val palette = AppContext.palette

        Button(Lucide.History, "History", palette.blue)
        Button(Lucide.HeartPulse, "Favorites", palette.red)
        Button(Lucide.TrendingUp, "Wrapped", palette.green)
        Button(Lucide.Origami, "Surprise me", palette.mauve)
    }
}

@Composable
private fun RowScope.Button(
    icon: ImageVector,
    label: String,
    color: Color
) {
    val palette = AppContext.palette

    Column(
        Modifier
            .weight(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(palette.crust.interpolate(color, 0.25f))
            .padding(4.dp, 16.dp)
        ,
        Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        Alignment.CenterHorizontally
    ) {
        Icon(icon, color)
        Text(
            label,
            Modifier,
            color,
            style = MaterialTheme.typography.labelMedium
        )
    }
}