package fr.yggz.android.lyricscollection.presentation.main.song

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.yggz.android.lyricscollection.databinding.SongItemBinding
import fr.yggz.android.lyricscollection.models.ui.Song

class SongAdapter(var songList: List<Song>) : RecyclerView.Adapter<SongViewHolder>(){
    private lateinit var binding : SongItemBinding
    lateinit var onFavClick: ((Song?) -> Unit)
    lateinit var onItemClick: ((Song?) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        binding = SongItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songList[position]
        holder.bind(song, onFavClick, onItemClick)
    }

    override fun getItemCount(): Int = songList.size

}