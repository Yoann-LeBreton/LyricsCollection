package fr.yggz.android.lyricscollection.data.datasources

import fr.yggz.android.lyricscollection.data.database.SongDao
import fr.yggz.android.lyricscollection.models.database.SongDb
import kotlinx.coroutines.flow.Flow

class SongsLocalDataSourceImpl(private val songDao: SongDao) : SongsLocalDataSource{

    override suspend fun insertSongs(songs: List<SongDb>) {
        songDao.insert(songs)
    }
    override suspend fun getSongs(): Flow<List<SongDb>> {
        return songDao.getSongs()
    }
}