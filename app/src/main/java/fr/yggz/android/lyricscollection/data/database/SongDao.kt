package fr.yggz.android.lyricscollection.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.yggz.android.lyricscollection.models.database.SongDb
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(songs: List<SongDb>)

    @Query("SELECT * FROM song_table")
    suspend fun getSongs(): Flow<List<SongDb>>
}