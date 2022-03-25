package fr.yggz.android.lyricscollection.models.entities


data class SongEntity(
    val albumId: Int,
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String,
)