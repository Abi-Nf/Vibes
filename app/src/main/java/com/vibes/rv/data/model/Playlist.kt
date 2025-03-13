package com.vibes.rv.data.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vibes.rv.util.now

@Entity
data class Playlist(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String,
    val image: Uri,
    val addedAt: Long = now()
)