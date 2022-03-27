package fr.yggz.android.lyricscollection.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.yggz.android.lyricscollection.models.database.SongDb
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(songs: List<SongDb>)

    @Query("SELECT * FROM song_table")
    fun getSongs(): Flow<List<SongDb>>

    @Query("SELECT * FROM song_table WHERE album_id IN(:albumId)")
    fun getSongsbyAlbumId(albumId: Int): Flow<List<SongDb>>
}