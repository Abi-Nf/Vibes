package com.vibes.rv.data.repository

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.vibes.rv.data.dto.PlaylistDto
import com.vibes.rv.data.model.Playlist

@Dao
interface PlaylistDao {
    @Upsert
    suspend fun savePlaylist(playlist: Playlist)

    @Query("SELECT * FROM `playlist`")
    suspend fun getPlaylists(): List<Playlist>

    @Query("SELECT * FROM `playlist` WHERE id = :id")
    suspend fun getById(id: Long): PlaylistDto?
}