package fr.yggz.android.lyricscollection.domain.repositories

import fr.yggz.android.lyricscollection.domain.common.Result
import fr.yggz.android.lyricscollection.models.database.AlbumDb
import fr.yggz.android.lyricscollection.models.database.SongDb
import kotlinx.coroutines.flow.Flow

interface SongsRepository {
    suspend fun syncSongs(): Result<String>
    suspend fun getSongs(): Flow<List<SongDb>>
    suspend fun getAlbums(): Flow<List<AlbumDb>>
    suspend fun setAlbumFavorite(albumId: Int, favorite: Boolean)
    suspend fun setSongFavorite(songId: Int, favorite: Boolean)
    suspend fun getSongsByAlbumId(albumId: Int): Flow<List<SongDb>>
}