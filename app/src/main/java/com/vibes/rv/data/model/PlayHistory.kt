package com.vibes.rv.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vibes.rv.util.now

@Entity
data class PlayHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val trackId: String, // id from content resolver
    val startPlaySecond: Long,
    val endPlaySecond: Long,
    val addedAt: Long = now()
) {
    fun getDuration() = endPlaySecond - startPlaySecond
}
