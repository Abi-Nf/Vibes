package com.vibes.rv.data.dto

import android.net.Uri
import com.vibes.rv.util.now

data class Track(
    val id: Long,
    val title: String,
    val album: Album,
    val feats: List<Artist>?,
    val trackNumber: Int,
    val duration: Long, // in millisecond
    val source: Uri,
    val size: Long,
    val addedAt: Long = now()
)