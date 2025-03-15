package com.vibes.rv.data.repository

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.vibes.rv.data.dto.Artist

class ArtistRepository(
    private val context: Context
) {
    private val projection get() = arrayOf(
        MediaStore.Audio.Artists._ID,
        MediaStore.Audio.Artists.ARTIST,
    )

    private fun query(
        selection: String? = null,
        selectionArgs: Array<String>? = null,
        sortOrder: String? = null
    ): Cursor? {
        return context.contentResolver.query(
            MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )
    }

    fun getArtists(): List<Artist> {
        var list = mutableListOf<Artist>()
        query()?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Artists._ID)
            while (it.moveToNext()) {
                val idValue = it.getLong(idColumn)
            }
        }
        return list
    }

    fun getById(id: Long): Artist? {
        return query(
            selection = "${MediaStore.Audio.Artists._ID} = ?",
            selectionArgs = arrayOf(id.toString()),
            sortOrder = "${MediaStore.Audio.Artists.ARTIST} ASC"
        )?.use { it.getOne() }
    }
}

private fun Cursor.getOne(): Artist? {
    val idColumn = getColumnIndexOrThrow(MediaStore.Audio.Artists._ID)
    val artistColumn = getColumnIndexOrThrow(MediaStore.Audio.Artists.ARTIST)
    if (moveToNext()) return mapArtist(idColumn, artistColumn)
    return null
}

private fun Cursor.toList(list: MutableList<Artist> = mutableListOf()): MutableList<Artist> {
    val idColumn = getColumnIndexOrThrow(MediaStore.Audio.Artists._ID)
    val artistColumn = getColumnIndexOrThrow(MediaStore.Audio.Artists.ARTIST)
    while (moveToNext()) {
        list.add(mapArtist(idColumn, artistColumn))
    }
    return list
}

private fun Cursor.mapArtist(
    idIndex: Int,
    nameIndex: Int
): Artist {
    return Artist(
        getLong(idIndex),
        getString(nameIndex)
    )
}
