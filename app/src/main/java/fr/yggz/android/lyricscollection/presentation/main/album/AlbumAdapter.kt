package fr.yggz.android.lyricscollection.presentation.main.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.yggz.android.lyricscollection.databinding.AlbumItemBinding
import fr.yggz.android.lyricscollection.models.ui.Album

class AlbumAdapter(var albumList: List<Album>) : RecyclerView.Adapter<AlbumViewHolder>(){
    private lateinit var binding: AlbumItemBinding
    lateinit var onFavClick: ((Album?) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        binding = AlbumItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albumList[position]
        holder.bind(album, onFavClick)
    }

    override fun getItemCount(): Int = albumList.size
}