package fr.yggz.android.lyricscollection.data.di

import android.app.Application
import androidx.room.Room
import fr.yggz.android.lyricscollection.data.database.AlbumDao
import fr.yggz.android.lyricscollection.data.database.LyricsDatabase
import fr.yggz.android.lyricscollection.data.database.SongDao
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

val DatabaseModule : Module = module {
    fun provideDatabase(application: Application): LyricsDatabase{
        return Room.databaseBuilder(application, LyricsDatabase::class.java, "lyrics_database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
    fun provideSongDao(database: LyricsDatabase) : SongDao {
        return database.songDao
    }
    fun provideAlbumDao(database: LyricsDatabase) : AlbumDao {
        return database.albumDao
    }

    single<LyricsDatabase> { provideDatabase(androidApplication()) }
    single<SongDao> { provideSongDao(get()) }
    single<AlbumDao> { provideAlbumDao(get()) }
}