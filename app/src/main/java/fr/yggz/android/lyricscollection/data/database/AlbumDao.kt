package fr.yggz.android.lyricscollection.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.yggz.android.lyricscollection.models.database.AlbumDb
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(albums: List<AlbumDb>)

    @Query("SELECT * FROM album_table")
    fun getAlbums(): Flow<List<AlbumDb>>

    @Query("UPDATE album_table SET favorite = :favorite WHERE id = :albumId")
    fun updateFavorite(favorite: Boolean, albumId: Int)
}