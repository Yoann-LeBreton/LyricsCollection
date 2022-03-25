package fr.yggz.android.lyricscollection.models.ui

sealed class Song(
    val id: Int,
    val title: String,
    val favorite: Boolean,
    val pictureUrl: String,
    val thumbnailUrl: String
)