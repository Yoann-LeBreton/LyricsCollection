package fr.yggz.android.lyricscollection.models.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AlbumDao(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val favorite: Boolean
)