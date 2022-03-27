package fr.yggz.android.lyricscollection.domain.repositories

import fr.yggz.android.lyricscollection.domain.common.Result
import fr.yggz.android.lyricscollection.models.database.SongDb
import kotlinx.coroutines.flow.Flow

interface SongsRepository {
    suspend fun syncSongs(): Result<String>
    suspend fun getSongs(): Flow<List<SongDb>>
}