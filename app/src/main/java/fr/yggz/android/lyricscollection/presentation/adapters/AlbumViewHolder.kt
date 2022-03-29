package fr.yggz.android.lyricscollection.presentation.adapters

import androidx.recyclerview.widget.RecyclerView
import fr.yggz.android.lyricscollection.databinding.AlbumItemBinding
import fr.yggz.android.lyricscollection.models.ui.Album

class AlbumViewHolder(private val binding: AlbumItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(album: Album, onFavClick: (Album?) -> Unit, onItemClick: (Album?) -> Unit) {
        binding.album = album
        binding.itemAlbumFav.setOnClickListener {
            onFavClick(album)
        }
        binding.itemAlbumZoneclick.setOnClickListener {
            onItemClick(album)
        }
    }
}