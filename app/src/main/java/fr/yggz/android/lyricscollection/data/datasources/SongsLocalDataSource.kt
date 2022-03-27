package fr.yggz.android.lyricscollection.data.datasources

import fr.yggz.android.lyricscollection.models.database.SongDb
import kotlinx.coroutines.flow.Flow

interface SongsLocalDataSource {
    suspend fun insertSongs(songs: List<SongDb>)
    suspend fun getSongs(): Flow<List<SongDb>>
    suspend fun getSongsByAlbumId(albumId: Int): Flow<List<SongDb>>
}