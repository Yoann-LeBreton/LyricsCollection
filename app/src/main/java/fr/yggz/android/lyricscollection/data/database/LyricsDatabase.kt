package fr.yggz.android.lyricscollection.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.yggz.android.lyricscollection.models.database.AlbumDb
import fr.yggz.android.lyricscollection.models.database.SongDb

@Database(
    entities = [
        SongDb::class],
    version = 1,
    exportSchema = false)

abstract class LyricsDatabase : RoomDatabase(){
    abstract val songDao : SongDao
}