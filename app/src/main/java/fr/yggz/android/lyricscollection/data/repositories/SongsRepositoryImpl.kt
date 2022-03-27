package fr.yggz.android.lyricscollection.data.repositories

import android.util.Log
import fr.yggz.android.lyricscollection.data.datasources.AlbumLocalDataSource
import fr.yggz.android.lyricscollection.data.datasources.SongsLocalDataSource
import fr.yggz.android.lyricscollection.data.datasources.SongsRemoteDataSource
import fr.yggz.android.lyricscollection.data.entities.SongResponse
import fr.yggz.android.lyricscollection.domain.common.Constants
import fr.yggz.android.lyricscollection.domain.common.Result
import fr.yggz.android.lyricscollection.domain.repositories.SongsRepository
import fr.yggz.android.lyricscollection.models.database.AlbumDb
import fr.yggz.android.lyricscollection.models.database.SongDb
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class SongsRepositoryImpl : SongsRepository , KoinComponent{
    private val remoteDataSource: SongsRemoteDataSource by inject()
    private val songsLocalDataSource: SongsLocalDataSource by inject()
    private val albumLocalDataSource: AlbumLocalDataSource by inject()

    override suspend fun syncSongs(): Result<String> {
        return when(val resultSongs = remoteDataSource.getSongs()){
            is Result.Success -> {
                try {
                    if(resultSongs.data != null && resultSongs.data.isNotEmpty()) {
                        val albums : List<AlbumDb> = resultSongs.data
                            .groupBy { it.albumId }
                            .map { (key, _) -> AlbumDb(id = key, title = "Album n°$key", favorite = false)}
                        songsLocalDataSource.insertSongs(resultSongs.data.map {
                            SongDb(id = it.id, albumId = it.albumId, title = it.title, favorite = false, pictureUrl = it.url, it.thumbnailUrl)
                        })
                        albumLocalDataSource.insertAlbums(albums)
                        val now = Date(Timestamp(System.currentTimeMillis()).time)
                        val formatter = SimpleDateFormat(Constants.DATE_FORMAT, Locale.FRANCE)
                        Result.Success(formatter.format(now))
                    }else{
                        Result.Error(Exception("No data retrieve"))
                    }

                }catch(ex: Exception){
                    Result.Error(ex)
                }
            }
            is Result.Error -> {
                resultSongs
            }
        }
    }

    override suspend fun getSongs(): Flow<List<SongDb>> =  songsLocalDataSource.getSongs()
}