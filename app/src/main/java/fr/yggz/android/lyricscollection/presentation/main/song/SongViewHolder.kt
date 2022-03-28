package fr.yggz.android.lyricscollection.presentation.main.song

import androidx.recyclerview.widget.RecyclerView
import fr.yggz.android.lyricscollection.databinding.SongItemBinding
import fr.yggz.android.lyricscollection.models.ui.Song

class SongViewHolder(private val binding: SongItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(song: Song, onFavClick: (Song?) -> Unit, onItemClick: (Song?) -> Unit){
        binding.song = song
        binding.itemSongFav.setOnClickListener {
            onFavClick(song)
        }
        binding.itemSongZoneclick.setOnClickListener {
            onItemClick(song)
        }
    }
}