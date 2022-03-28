package fr.yggz.android.lyricscollection.data.datasources

import fr.yggz.android.lyricscollection.data.api.SongsApi
import fr.yggz.android.lyricscollection.data.entities.SongResponse
import fr.yggz.android.lyricscollection.domain.common.ManageExceptions
import fr.yggz.android.lyricscollection.domain.common.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SongsRemoteDataSourceImpl(private val songsApi: SongsApi) : SongsRemoteDataSource{
    override suspend fun getSongs(): Result<List<SongResponse>?> =
        withContext(Dispatchers.IO){
            try{
                val response = songsApi.getSongs()
                if(response.isSuccessful){
                    return@withContext Result.Success(response.body())
                }
                return@withContext Result.Error(ManageExceptions.manageNetworkError(response.code(), response.message()))
            }catch (ex: Exception){
                return@withContext Result.Error(ManageExceptions.UnknowException(ex.message ?: "Unknow error from remoteDatasource"))
            }
        }
}