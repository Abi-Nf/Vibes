package com.vibes.rv.data.repository

import com.vibes.rv.data.dto.Album
import com.vibes.rv.data.dto.Artist

val UNKNOWN_ARTIST = Artist(0, "Unknown Artist")
val UNKNOWN_ALBUM = Album(0, "Unknown Album", null, UNKNOWN_ARTIST, 0, listOf())