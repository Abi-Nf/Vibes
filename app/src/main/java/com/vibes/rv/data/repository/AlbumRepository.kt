package com.vibes.rv.data.repository;

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.vibes.rv.data.dto.Album
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

public class AlbumRepository(
    private val context: Context
) {
    private val projection get() = arrayOf(
        MediaStore.Audio.Albums._ID,
        MediaStore.Audio.Albums.ARTIST_ID,
        MediaStore.Audio.Albums.ALBUM,
        MediaStore.Audio.Albums.LAST_YEAR
    )

    private val artistRepository get() = ArtistRepository(context)

    private fun query(
        selection: String? = null,
        selectionArgs: Array<String>? = null,
        sortOrder: String? = "${MediaStore.Audio.Albums.ALBUM} DESC"
    ): Cursor? {
        return context.contentResolver.query(
            MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )
    }

    fun getById(id: Long): Album? {
        return query(
            selection = "${MediaStore.Audio.Albums._ID} = ?",
            selectionArgs = arrayOf(id.toString()),
            sortOrder = "${MediaStore.Audio.Albums.ALBUM} DESC"
        )?.use {
            it.getOne(artistRepository)
        }
    }

    fun getAllAlbums(): List<Album>? {
        return query()?.use {
            it.toList(artistRepository)
        }
    }

    fun fetchAlbums(): Flow<List<Album>> = context.contentResolver
        .observe(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI)
        .map { getAllAlbums().orEmpty() }
}

private fun Cursor.toList(artistRepository: ArtistRepository): List<Album> {
    var result = mutableListOf<Album>()
    val idIndex = getColumnIndexOrThrow(MediaStore.Audio.Albums._ID)
    val nameIndex = getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM)
    val artistIdIndex = getColumnIndexOrThrow(MediaStore.Audio.Albums.ARTIST_ID)
    val yearIndex = getColumnIndexOrThrow(MediaStore.Audio.Albums.LAST_YEAR)

    while(moveToNext()) {
        result.add(mapAlbum(idIndex, nameIndex, artistIdIndex, yearIndex, artistRepository))
    }

    return result
}

private fun Cursor.getOne(artistRepository: ArtistRepository): Album? {
    val idIndex = getColumnIndexOrThrow(MediaStore.Audio.Albums._ID)
    val nameIndex = getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM)
    val artistIdIndex = getColumnIndexOrThrow(MediaStore.Audio.Albums.ARTIST_ID)
    val yearIndex = getColumnIndexOrThrow(MediaStore.Audio.Albums.LAST_YEAR)

    while(moveToNext()) {
        return mapAlbum(idIndex, nameIndex, artistIdIndex, yearIndex, artistRepository)
    }

    return null
}


private fun Cursor.mapAlbum(
    idIndex: Int,
    nameIndex: Int,
    artistIndex: Int,
    yearIndex: Int,
    artistRepository: ArtistRepository
): Album {
    val id = getLong(idIndex)
    return Album(
        id,
        getString(nameIndex),
        ContentUris.withAppendedId(
            MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
            id
        ),
        artistRepository.getById(getLong(artistIndex)) ?: UNKNOWN_ARTIST,
        getInt(yearIndex),
        listOf()
    )
}