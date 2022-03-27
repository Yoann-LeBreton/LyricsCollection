package fr.yggz.android.lyricscollection.models.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album_table")
data class AlbumDb(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val favorite: Boolean
)