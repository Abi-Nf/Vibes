package com.vibes.rv.data.repository

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.vibes.rv.data.dto.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date

class TrackRepository(private val context: Context) {

    private val artistRepository get() = ArtistRepository(context)
    private val albumRepository get() = AlbumRepository(context)
    private val projection
        get() = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.GENRE,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.TRACK
        )

    private fun query(
        selection: String? = null,
        selectionArgs: Array<String>? = null,
        sortOrder: String? = "${MediaStore.Audio.Media.TITLE} ASC"
    ): Cursor? {
        return context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )
    }

    fun getById(id: Long): Track? {
        return query(
            selection = "${MediaStore.Audio.Media._ID} = ?",
            selectionArgs = arrayOf(id.toString()),
        )?.use {
            it.getOne(albumRepository, artistRepository)
        }
    }

    fun getTracks(): List<Track>? {
        return query()?.use {
            it.toList(albumRepository, artistRepository)
        }
    }

    fun fetchTracks(): Flow<List<Track>> = context.contentResolver
        .observe(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
            .map { getTracks().orEmpty() }

    fun getAllByArtistId(artistId: Long): List<Track> {
        return query(
            selection = "${MediaStore.Audio.Media.ARTIST_ID} = ?",
            selectionArgs = arrayOf(artistId.toString()),
        )?.use {
            it.toList(albumRepository, artistRepository)
        } ?: emptyList()
    }

    fun getAllByAlbumId(albumId: Long): List<Track> {
        return query(
            selection = "${MediaStore.Audio.Media.ALBUM_ID} = ?",
            selectionArgs = arrayOf(albumId.toString()),
        )?.use {
            it.toList(albumRepository, artistRepository)
        } ?: emptyList()
    }
}

private fun Cursor.getOne(
    albumRepository: AlbumRepository,
    artistRepository: ArtistRepository
): Track? {
    val idColumn = getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
    val titleColumn = getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
    val trackNumberColumn = getColumnIndexOrThrow(MediaStore.Audio.Media.TRACK)
    val durationColumn = getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
    val sizeColumn = getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
    val addedAtColumn = getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)
    val albumColumn = getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
    val artistColumn = getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST_ID)

    while (moveToNext()) {
        return mapTrack(
            idColumn,
            titleColumn,
            artistColumn,
            albumColumn,
            trackNumberColumn,
            durationColumn,
            sizeColumn,
            addedAtColumn,
            albumRepository,
            artistRepository
        )
    }
    return null
}

private fun Cursor.toList(
    albumRepository: AlbumRepository,
    artistRepository: ArtistRepository
): List<Track> {
    var result = mutableListOf<Track>()
    val idColumn = getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
    val titleColumn = getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
    val trackNumberColumn = getColumnIndexOrThrow(MediaStore.Audio.Media.TRACK)
    val durationColumn = getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
    val sizeColumn = getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
    val addedAtColumn = getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)
    val albumColumn = getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
    val artistColumn = getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST_ID)

    while (moveToNext()) {
        result.add(
            mapTrack(
                idColumn,
                titleColumn,
                artistColumn,
                albumColumn,
                trackNumberColumn,
                durationColumn,
                sizeColumn,
                addedAtColumn,
                albumRepository,
                artistRepository
            )
        )
    }

    return result
}

private fun Cursor.mapTrack(
    idIndex: Int,
    titleIndex: Int,
    artistIndex: Int,
    albumIndex: Int,
    trackNumberIndex: Int,
    durationIndex: Int,
    sizeIndex: Int,
    addedAtIndex: Int,
    albumRepository: AlbumRepository,
    artistRepository: ArtistRepository
): Track {
    return Track(
        getLong(idIndex),
        getString(titleIndex),
        albumRepository.getById(getLong(albumIndex)) ?: UNKNOWN_ALBUM,
        artistRepository.getById(getLong(artistIndex)) ?: UNKNOWN_ARTIST,
        null,
        getInt(trackNumberIndex),
        getLong(durationIndex),
        getLong(sizeIndex),
        Date(getLong(addedAtIndex))
    )
}