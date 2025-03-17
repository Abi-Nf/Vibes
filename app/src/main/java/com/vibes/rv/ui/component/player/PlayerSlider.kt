package com.vibes.rv.ui.component.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vibes.rv.ui.provider.AppContext
import com.vibes.rv.ui.util.toReadableTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerSlider(
    currentTime: Long,
    duration: Long
) {
    val palette = AppContext.palette
    val player = AppContext.player

    Column(
        Modifier
            .padding(22.dp, 8.dp)
    ) {
        var tempSliderValue by remember { mutableStateOf<Float?>(null) }

        Slider(
            tempSliderValue ?: currentTime.toFloat(),
            { tempSliderValue = it },
            Modifier.fillMaxWidth(),
            valueRange = 0f..duration.toFloat(),
            onValueChangeFinished = {
                tempSliderValue?.let { player?.seekTo(it.toLong()) }
                tempSliderValue = null
            }
        )

        Row(
            Modifier.fillMaxWidth(),
            Arrangement.SpaceBetween,
            Alignment.CenterVertically
        ) {
            val style = MaterialTheme.typography.labelLarge

            Text(
                currentTime.toReadableTime(),
                color = MaterialTheme.colorScheme.surfaceBright,
                style = style
            )

            Text(
                duration.toReadableTime(),
                color = MaterialTheme.colorScheme.surfaceBright,
                style = style
            )
        }
    }
}