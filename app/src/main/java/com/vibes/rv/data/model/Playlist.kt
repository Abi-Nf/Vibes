package com.vibes.rv.data.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.Instant.now

@Entity
data class Playlist(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String,
    val addedAt: Instant = now()
)