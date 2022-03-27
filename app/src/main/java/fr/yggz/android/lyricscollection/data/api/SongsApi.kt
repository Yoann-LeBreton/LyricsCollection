package fr.yggz.android.lyricscollection.data.api

import fr.yggz.android.lyricscollection.data.entities.SongResponse
import retrofit2.Response
import retrofit2.http.GET

// Fetch remote songs
interface SongsApi {
    @GET("daf1366561d97547407d4f471ac85004/raw/efd91bd919d99404142408bd19a88571ce6c46e3/song_collection.json")
    suspend fun getSongs(): Response<List<SongResponse>>
}