package fr.yggz.android.lyricscollection.presentation.main.album

import androidx.recyclerview.widget.RecyclerView
import fr.yggz.android.lyricscollection.databinding.AlbumItemBinding
import fr.yggz.android.lyricscollection.models.ui.Album

class AlbumViewHolder(private val binding: AlbumItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(album: Album){
        binding.album = album
    }
}