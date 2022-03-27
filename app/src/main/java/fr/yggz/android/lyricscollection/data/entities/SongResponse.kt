package fr.yggz.android.lyricscollection.data.entities


data class SongResponse(
    val albumId: Int,
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String,
)