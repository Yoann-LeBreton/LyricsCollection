package fr.yggz.android.lyricscollection.models.ui

sealed class Album(
    val id: Int,
    val title: String,
    val favorite: Boolean,
    val songs: List<Song>
)