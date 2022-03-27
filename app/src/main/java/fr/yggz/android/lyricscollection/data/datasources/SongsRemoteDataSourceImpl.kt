package fr.yggz.android.lyricscollection.data.datasources

import fr.yggz.android.lyricscollection.data.api.SongsApi
import fr.yggz.android.lyricscollection.data.entities.SongEntity
import fr.yggz.android.lyricscollection.domain.common.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SongsRemoteDataSourceImpl : SongsRemoteDataSource, KoinComponent{
    private val songsApi: SongsApi by inject()

    override suspend fun getSongs(): Result<List<SongEntity>?> =
        withContext(Dispatchers.IO){
            try{
                val response = songsApi.getSongs()
                if(response.isSuccessful){
                    return@withContext Result.Success(response.body())
                }
                return@withContext Result.Error(Exception(response.message()))
            }catch (ex: Exception){
                return@withContext Result.Error(ex)
            }
        }
}