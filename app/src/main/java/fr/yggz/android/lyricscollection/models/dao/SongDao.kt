package fr.yggz.android.lyricscollection.models.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SongDao(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "album_id")
    val albumId: Int,
    val title: String,
    val favorite: Boolean,
    @ColumnInfo(name = "picture_url")
    val pictureUrl: String,
    @ColumnInfo(name = "thumbnail_url")
    val thumbnailUrl: String
)