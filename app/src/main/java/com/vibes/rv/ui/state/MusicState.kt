package com.vibes.rv.ui.state

import androidx.compose.runtime.Stable
import androidx.media3.common.MediaItem

@Stable
data class MusicState(
    val currentTime: Long,
    val isPlaying: Boolean,
    val hasPrev: Boolean,
    val hasNext: Boolean,
    val repeatMode: Int,
    val isShuffle: Boolean,
    val playingQueueLength: Int,
    val currentMedia: MediaItem?
)
