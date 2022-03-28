package fr.yggz.android.lyricscollection.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import fr.yggz.android.lyricscollection.models.database.AlbumDb
import fr.yggz.android.lyricscollection.models.database.SongDb
import junit.framework.TestCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LyricsDatabaseTest : TestCase() {
    private lateinit var lyricsDatabase: LyricsDatabase
    private lateinit var albumDao: AlbumDao
    private lateinit var songDao: SongDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        lyricsDatabase = Room.inMemoryDatabaseBuilder(context, LyricsDatabase::class.java).build()
        albumDao = lyricsDatabase.albumDao
        songDao = lyricsDatabase.songDao
    }

    @After
    fun closeDb(){
        lyricsDatabase.close()
    }

    @Test
    fun testWriteAndReadSongDao() = runBlocking{
        val song : SongDb = SongDb(id = 22, albumId = 3, title = "Sing Song", favorite = false, pictureUrl = "pic.png", thumbnailUrl = "thumb.png")
        songDao.insert(listOf(song))
        val songs = songDao.getSongs().first()
        assertTrue(songs.contains(song))
    }

    @Test
    fun testWriteAndReadAlbumDao() = runBlocking {
        val album : AlbumDb = AlbumDb(id = 88, title = "Album 88", favorite = false)
        albumDao.insert(listOf(album))
        val albums = albumDao.getAlbums().first()
        assertTrue(albums.contains(album))
    }

    @Test
    fun updateFavoriteSongDao() = runBlocking{
        val song : SongDb = SongDb(id = 22, albumId = 3, title = "Sing Song", favorite = false, pictureUrl = "pic.png", thumbnailUrl = "thumb.png")
        songDao.insert(listOf(song))
        songDao.updateFavorite(song.id, !song.favorite)
        val songs = songDao.getSongs().first()
        assertFalse(songs.contains(song))
        assertTrue(songs[0].favorite == !song.favorite)
    }

    @Test
    fun updateFavoriteAlbumDao() = runBlocking{
        val album : AlbumDb = AlbumDb(id = 88, title = "Album 88", favorite = false)
        albumDao.insert(listOf(album))
        albumDao.updateFavorite(!album.favorite, album.id)
        val albums = albumDao.getAlbums().first()
        assertFalse(albums.contains(album))
        assertTrue(albums[0].favorite == !album.favorite)
    }
}