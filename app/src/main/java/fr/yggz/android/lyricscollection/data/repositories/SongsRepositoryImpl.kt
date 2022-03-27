package fr.yggz.android.lyricscollection.data.repositories

import fr.yggz.android.lyricscollection.data.datasources.SongsLocalDataSource
import fr.yggz.android.lyricscollection.data.datasources.SongsRemoteDataSource
import fr.yggz.android.lyricscollection.domain.repositories.SongsRepository
import fr.yggz.android.lyricscollection.domain.common.Result
import fr.yggz.android.lyricscollection.models.database.SongDb
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SongsRepositoryImpl : SongsRepository , KoinComponent{
    private val remoteDataSource: SongsRemoteDataSource by inject()
    private val localDataSource: SongsLocalDataSource by inject()

    override suspend fun syncSongs(): Result<String> {
        return when(val resultSongs = remoteDataSource.getSongs()){
            is Result.Success -> {
                try {
                    if(resultSongs.data != null && resultSongs.data.isNotEmpty()) localDataSource.insertSongs(resultSongs.data.map {
                        SongDb(id = it.id, albumId = it.albumId, title = it.title, favorite = false, pictureUrl = it.url, it.thumbnailUrl)
                    })
                    Result.Success("now")
                }catch(ex: Exception){
                    Result.Error(ex)
                }
            }
            is Result.Error -> {
                resultSongs
            }
        }
    }

    override suspend fun getSongs(): Flow<List<SongDb>> = localDataSource.getSongs()
}