package com.vibes.rv.data.dto

import android.content.ContentUris
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import java.util.Date

data class Track(
    val id: Long,
    val title: String,
    val album: Album,
    val artist: Artist,
    val feats: List<Artist>?,
    val trackNumber: Int,
    val duration: Long, // in millisecond
    val size: Long,
    val addedAt: Date,
    val source: Uri = ContentUris.withAppendedId(
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
        id
    ),
    val thumbnailUri: Uri? = "${source}/albumart}".toUri()
) {
    @OptIn(UnstableApi::class)
    fun toMediaItem(): MediaItem {
        return MediaItem
            .Builder()
            .setUri(source)
            .setMediaId(id.toString())
            .setMediaMetadata(
                MediaMetadata
                    .Builder()
                    .setIsBrowsable(false)
                    .setIsPlayable(true)
                    .setTitle(title)
                    .setArtist(artist.name)
                    .setAlbumTitle(album.name)
                    .setArtworkUri(thumbnailUri)
                    .setDurationMs(duration)
                    .setTrackNumber(trackNumber)
                    .setExtras(
                        Bundle().apply {
                            putString("source", source.toString())
                            putLong("album_id", album.id)
                            putLong("artist_id", artist.id)
                            putLong("mediaId", id)
                            putLong("size", size)
                        }
                    ).build()
            )
            .build()
    }
}