package com.vibes.rv.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.Instant.now

@Entity
data class Like(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val trackId: String, // id from content resolver
    val addedAt: Instant = now()
)
