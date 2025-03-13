package com.vibes.rv.data.dto

import androidx.room.Embedded
import androidx.room.Relation
import com.vibes.rv.data.model.Playlist
import com.vibes.rv.data.model.PlaylistItem

data class PlaylistDto(
    @Embedded
    val playlist: Playlist,
    @Relation(
        parentColumn = "id",
        entityColumn = "playlistId"
    )
    val list: List<PlaylistItem>
) {
    val length get() = list.size

    operator fun get(index: Int) = list.getOrNull(index)
}