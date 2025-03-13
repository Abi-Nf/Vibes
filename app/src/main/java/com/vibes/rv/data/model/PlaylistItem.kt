package com.vibes.rv.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.vibes.rv.util.now

@Entity(foreignKeys = [
    ForeignKey(
        entity = Playlist::class,
        parentColumns = ["id"],
        childColumns = ["playlistId"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )
])
data class PlaylistItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val playlistId: Long,
    val trackId: Long,
    val addedAt: Long = now()
)