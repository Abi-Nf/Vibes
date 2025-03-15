package com.vibes.rv.data.dto

import android.content.Context
import android.net.Uri
import android.provider.MediaStore

data class Album(
    val id: Long,
    val name: String,
    val image: Uri?,
    val artist: Artist,
    val year: Int,
    val categories: List<Category>
)
