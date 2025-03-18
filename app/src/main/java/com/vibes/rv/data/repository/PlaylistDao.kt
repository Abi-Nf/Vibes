package com.vibes.rv.data.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.vibes.rv.data.dto.PlaylistDto
import com.vibes.rv.data.model.Playlist
import com.vibes.rv.data.model.PlaylistItem
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {
    @Upsert
    suspend fun savePlaylist(playlist: Playlist)

    @Delete
    suspend fun delete(playlist: Playlist)

    @Query("SELECT * FROM `playlist`")
    fun getPlaylists(): Flow<List<Playlist>>

    @Upsert
    suspend fun savePlaylistItem(playlistItem: PlaylistItem)

    @Query("SELECT * FROM `playlist` WHERE id = :id")
    fun getById(id: Long): Flow<PlaylistDto?>
}