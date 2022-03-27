package fr.yggz.android.lyricscollection.data.datasources

import fr.yggz.android.lyricscollection.data.entities.SongResponse
import fr.yggz.android.lyricscollection.domain.common.Result

interface SongsRemoteDataSource {
    suspend fun getSongs(): Result<List<SongResponse>?>
}