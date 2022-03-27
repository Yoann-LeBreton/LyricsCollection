package fr.yggz.android.lyricscollection.data.datasources

import fr.yggz.android.lyricscollection.models.database.AlbumDb
import kotlinx.coroutines.flow.Flow

interface AlbumLocalDataSource {
    suspend fun insertAlbums(albums: List<AlbumDb>)
    suspend fun getAlbums(): Flow<List<AlbumDb>>
    suspend fun setAlbumFavorite(albumId: Int, favorite:Boolean)
}