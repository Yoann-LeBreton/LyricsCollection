package fr.yggz.android.lyricscollection.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.yggz.android.lyricscollection.BuildConfig
import fr.yggz.android.lyricscollection.models.database.AlbumDb
import fr.yggz.android.lyricscollection.models.database.SongDb

@Database(
    entities = [SongDb::class, AlbumDb::class],
    version = BuildConfig.DATABASE_VERSION,
    exportSchema = false
)

abstract class LyricsDatabase : RoomDatabase() {
    abstract val songDao: SongDao
    abstract val albumDao: AlbumDao
}