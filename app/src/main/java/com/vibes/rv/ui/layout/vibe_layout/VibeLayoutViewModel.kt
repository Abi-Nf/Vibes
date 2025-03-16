package com.vibes.rv.ui.layout.vibe_layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel

internal class VibeLayoutViewModel : ViewModel() {
    var contentCompactHeight by mutableStateOf(0.dp)
    var content by mutableStateOf<@Composable (() -> Unit)?>(null)
}