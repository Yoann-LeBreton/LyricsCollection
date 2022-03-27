package fr.yggz.android.lyricscollection.data.datasources

import fr.yggz.android.lyricscollection.data.database.AlbumDao
import fr.yggz.android.lyricscollection.models.database.AlbumDb
import kotlinx.coroutines.flow.Flow

class AlbumLocalDataSourceImpl(private val albumDao: AlbumDao) : AlbumLocalDataSource {

    override suspend fun insertAlbums(albums: List<AlbumDb>) {
        albumDao.insert(albums)
    }

    override suspend fun getAlbums(): Flow<List<AlbumDb>> {
        return albumDao.getAlbums()
    }

    override suspend fun setAlbumFavorite(albumId: Int, favorite: Boolean) {
        return albumDao.updateFavorite(favorite, albumId)
    }
}