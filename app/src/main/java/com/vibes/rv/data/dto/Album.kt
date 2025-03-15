package com.vibes.rv.data.dto

import android.net.Uri

data class Album(
    val id: Long,
    val name: String,
    val image: Uri?,
    val artist: Artist,
    val year: Int,
    val categories: List<Category>
)
