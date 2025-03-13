package com.vibes.rv.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vibes.rv.util.now

@Entity
data class PlaylistItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val playlistId: Long,
    val trackId: Long,
    val addedAt: Long = now()
)