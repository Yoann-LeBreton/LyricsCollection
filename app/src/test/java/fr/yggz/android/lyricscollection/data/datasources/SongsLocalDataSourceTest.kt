package fr.yggz.android.lyricscollection.data.datasources

import fr.yggz.android.lyricscollection.data.database.SongDao
import fr.yggz.android.lyricscollection.data.entities.SongResponse
import fr.yggz.android.lyricscollection.models.database.SongDb
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

class SongsLocalDataSourceTest {
    private lateinit var songsLocalDataSource: SongsLocalDataSource
    private lateinit var songDao: SongDao

    private val localSongs : List<SongDb> = listOf(
        SongDb(1, 1, "song 1", false,"url_song_1", "thumb_url_song_1"),
        SongDb(1, 2, "song 2", false,"url_song_2", "thumb_url_song_2"),
        SongDb(1, 3, "song 3", false,"url_song_3", "thumb_url_song_3"),
        SongDb(1, 4, "song 4", false,"url_song_4", "thumb_url_song_4"),
    )

    @Before
    fun setUp(){
        songDao = mock(SongDao::class.java)
        songsLocalDataSource = SongsLocalDataSourceImpl(songDao)
    }

    @Test
    fun retrieveLocalSongs(): Unit = runBlocking{
        Mockito.`when`(songDao.getSongs()).thenReturn(flow {
            emit(localSongs)
        })
        val result = songsLocalDataSource.getSongs()
        assertEquals(localSongs, result.first())
        verify(songDao, times(1)).getSongs()
    }

    @Test
    fun retrieveFavoriteLocalSongs(): Unit = runBlocking{
        Mockito.`when`(songDao.getSongsbyAlbumId(1)).thenReturn(flow {
            emit(localSongs)
        })
        val result = songsLocalDataSource.getSongsByAlbumId(1)
        assertEquals(localSongs, result.first())
        verify(songDao, times(1)).getSongsbyAlbumId(1)
    }

    @Test
    fun insertLocalSongs(): Unit = runBlocking{
        Mockito.`when`(songDao.insert(localSongs)).thenReturn(any())
        songsLocalDataSource.insertSongs(localSongs)
        verify(songDao, times(1)).insert(localSongs)
    }

    @Test
    fun setSongFavorite(): Unit = runBlocking{
        Mockito.`when`(songDao.updateFavorite(2, true)).then{  }
        songsLocalDataSource.setSongFavorite(2, true)
        verify(songDao, times(1)).updateFavorite(2, true)
    }
}