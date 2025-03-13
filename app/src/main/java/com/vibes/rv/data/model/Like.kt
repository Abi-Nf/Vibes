package com.vibes.rv.data.model

import androidx.room.PrimaryKey
import com.vibes.rv.util.now

data class Like(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val trackId: String, // id from content resolver
    val addedAt: Long = now()
)
