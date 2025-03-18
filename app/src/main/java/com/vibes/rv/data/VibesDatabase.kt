package com.vibes.rv.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.vibes.rv.data.converter.InstantConverter
import com.vibes.rv.data.converter.UriConverter
import com.vibes.rv.data.model.Like
import com.vibes.rv.data.model.PlayHistory
import com.vibes.rv.data.model.Playlist
import com.vibes.rv.data.model.PlaylistItem
import com.vibes.rv.data.repository.LikeDao
import com.vibes.rv.data.repository.PlaylistDao

@Database(
    entities = [Like::class, PlayHistory::class, Playlist::class, PlaylistItem::class],
    version = 2
)
@TypeConverters(UriConverter::class, InstantConverter::class)
abstract class VibesDatabase : RoomDatabase() {
    companion object {
        val MIGRATION_1_2  = object: Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) = database.execSQL("ALTER TABLE `playlist` DROP COLUMN `image`")
        }

        fun init(context: Context): VibesDatabase {
            return Room.databaseBuilder(
                context = context,
                klass = VibesDatabase::class.java,
                name = "playlist.db"
            )
                .addMigrations(MIGRATION_1_2)
                .build()
        }
    }
    abstract val likeDao: LikeDao
    abstract val playlistDao: PlaylistDao

}
