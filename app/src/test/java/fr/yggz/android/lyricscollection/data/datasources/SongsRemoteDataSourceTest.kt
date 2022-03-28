package fr.yggz.android.lyricscollection.data.datasources

import fr.yggz.android.lyricscollection.data.api.SongsApi
import fr.yggz.android.lyricscollection.data.entities.SongResponse
import fr.yggz.android.lyricscollection.domain.common.Result
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.kotlin.verify
import retrofit2.Response

class SongsRemoteDataSourceTest {

    private lateinit var songsRemoteDataSource: SongsRemoteDataSource
    private lateinit var songsApi: SongsApi

    private val remoteSongs : List<SongResponse> = listOf(
        SongResponse(1, 1, "song 1", "url_song_1", "thumb_url_song_1"),
        SongResponse(1, 2, "song 2", "url_song_2", "thumb_url_song_2"),
        SongResponse(1, 3, "song 3", "url_song_3", "thumb_url_song_3"),
        SongResponse(1, 4, "song 4", "url_song_4", "thumb_url_song_4"),
    )
    private val responseSuccess : Response<List<SongResponse>> = Response.success(remoteSongs)

    @Before
    fun setUp(){
        songsApi = mock(SongsApi::class.java)
        songsRemoteDataSource = SongsRemoteDataSourceImpl(songsApi)
    }

    @Test
    fun successGetRemoteSongs(): Unit = runBlocking{
        Mockito.`when`(songsApi.getSongs()).thenReturn(responseSuccess)
        val response = songsRemoteDataSource.getSongs()
        assertTrue(response is Result.Success)
        assertEquals(remoteSongs, (response as Result.Success).data)
        verify(songsApi, times(1)).getSongs()
    }

    @Test
    fun errorGetRemoteSongs(): Unit = runBlocking{
        Mockito.`when`(songsApi.getSongs()).thenReturn(Response.error(403, ResponseBody.create(
            "application/json".toMediaTypeOrNull(), "{\"key\":[\"somestuff\"]}")))
        val response = songsRemoteDataSource.getSongs()
        assertTrue(response is Result.Error)
        verify(songsApi, times(1)).getSongs()
    }
}