package fr.yggz.android.lyricscollection.presentation.main.favorite

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import fr.yggz.android.lyricscollection.databinding.DialogDetailsSongBinding
import fr.yggz.android.lyricscollection.databinding.FragmentFavoriteBinding
import fr.yggz.android.lyricscollection.presentation.adapters.SongAdapter

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val _favoriteViewModel: FavoriteViewModel by viewModels()

    private val binding get() = _binding!!
    private lateinit var _songAdapter: SongAdapter
    private lateinit var _layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _songAdapter = SongAdapter(emptyList())
        _songAdapter.onFavClick = { song ->
            song?.let {
                _favoriteViewModel.updateSongFavorite(it.id, !it.favorite)
            }
        }
        _songAdapter.onItemClick = { song ->
            song?.let {
                val viewSongDetails = DialogDetailsSongBinding.inflate(
                    LayoutInflater.from(requireContext()),
                    binding.root as ViewGroup,
                    false
                )
                viewSongDetails.song = song
                val dialog = Dialog(requireContext())
                dialog.setContentView(viewSongDetails.root)
                dialog.show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerViewSongs = binding.recyclerViewFavorite
        recyclerViewSongs.adapter = _songAdapter
        _layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerViewSongs.layoutManager = _layoutManager
        recyclerViewSongs.setHasFixedSize(true)
        recyclerViewSongs.addItemDecoration(
            DividerItemDecoration(requireContext(), _layoutManager.orientation)
        )
        _favoriteViewModel.songs.observe(viewLifecycleOwner) {
            _songAdapter.songList = it
            _songAdapter.notifyDataSetChanged()
        }
        _favoriteViewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
        }
        _favoriteViewModel.getFavoriteSongs()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}