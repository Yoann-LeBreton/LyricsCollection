package fr.yggz.android.lyricscollection.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.yggz.android.lyricscollection.BuildConfig
import fr.yggz.android.lyricscollection.R

@Database(entities = [SongDao::class], version = 1, exportSchema = false)
abstract class LyricsDatabase : RoomDatabase(){
    abstract val songDao : SongDao
}