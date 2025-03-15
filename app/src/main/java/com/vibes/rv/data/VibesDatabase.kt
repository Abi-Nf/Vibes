package com.vibes.rv.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vibes.rv.data.converter.InstantConverter
import com.vibes.rv.data.converter.UriConverter
import com.vibes.rv.data.model.Like
import com.vibes.rv.data.model.PlayHistory
import com.vibes.rv.data.model.Playlist
import com.vibes.rv.data.model.PlaylistItem
import com.vibes.rv.data.repository.LikeDao

@Database(
    entities = [Like::class, PlayHistory::class, Playlist::class, PlaylistItem::class],
    version = 1
)
@TypeConverters(UriConverter::class, InstantConverter::class)
abstract class VibesDatabase : RoomDatabase() {
    companion object {
        fun init(context: Context): VibesDatabase {
            return Room.databaseBuilder(
                context = context,
                klass = VibesDatabase::class.java,
                name = "playlist.db"
            ).build()
        }
    }

    abstract val likeDao: LikeDao
}
