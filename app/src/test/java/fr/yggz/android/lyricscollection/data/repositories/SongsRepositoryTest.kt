package fr.yggz.android.lyricscollection.data.repositories

import fr.yggz.android.lyricscollection.application.LyricsApplication
import fr.yggz.android.lyricscollection.data.datasources.AlbumLocalDataSource
import fr.yggz.android.lyricscollection.data.datasources.SongsLocalDataSource
import fr.yggz.android.lyricscollection.data.datasources.SongsRemoteDataSource
import fr.yggz.android.lyricscollection.data.entities.SongResponse
import fr.yggz.android.lyricscollection.domain.common.Constants
import fr.yggz.android.lyricscollection.domain.common.Result
import fr.yggz.android.lyricscollection.domain.repositories.SongsRepository
import fr.yggz.android.lyricscollection.models.database.AlbumDb
import fr.yggz.android.lyricscollection.models.database.SongDb
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.kotlin.any

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Exception
import kotlin.reflect.typeOf


class SongsRepositoryTest {
    private lateinit var songsRepository: SongsRepository
    private lateinit var albumLocalDataSource: AlbumLocalDataSource
    private lateinit var songsLocalDataSource: SongsLocalDataSource
    private lateinit var remoteDataSource: SongsRemoteDataSource

    private val remoteSongs : List<SongResponse> = listOf(
        SongResponse(1, 1, "song 1", "url_song_1", "thumb_url_song_1"),
        SongResponse(1, 2, "song 2", "url_song_2", "thumb_url_song_2"),
        SongResponse(1, 3, "song 3", "url_song_3", "thumb_url_song_3"),
        SongResponse(1, 4, "song 4", "url_song_4", "thumb_url_song_4"),
    )

    @Before
    fun setUp() {
        albumLocalDataSource = mock(AlbumLocalDataSource::class.java)
        songsLocalDataSource = mock(SongsLocalDataSource::class.java)
        remoteDataSource = mock(SongsRemoteDataSource::class.java)
        songsRepository = SongsRepositoryImpl(remoteDataSource, songsLocalDataSource, albumLocalDataSource)
    }

    @Test
    fun successSyncRemoteData(): Unit = runBlocking{
        Mockito.`when`(remoteDataSource.getSongs()).thenReturn(Result.Success(remoteSongs))
        val syncResult = songsRepository.syncSongs()
        val now = Date(Timestamp(System.currentTimeMillis()).time)
        val dateNowStr = SimpleDateFormat(Constants.DATE_FORMAT, Locale.FRANCE).format(now)
        assertEquals(Result.Success(dateNowStr), syncResult)
        verify(remoteDataSource, times(1)).getSongs()
        verify(songsLocalDataSource, times(1)).insertSongs(any())
        verify(albumLocalDataSource, times(1)).insertAlbums(any())
    }

    @Test
    fun errorSyncRemoteData(): Unit = runBlocking {
        Mockito.`when`(remoteDataSource.getSongs()).thenReturn(Result.Error(Exception("failed to fetch data")))
        val syncResult = songsRepository.syncSongs()
        assertTrue(syncResult is Result.Error)
        verify(remoteDataSource, times(1)).getSongs()
        verify(songsLocalDataSource, times(0)).insertSongs(any())
        verify(albumLocalDataSource, times(0)).insertAlbums(any())
    }
}