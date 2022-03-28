package fr.yggz.android.lyricscollection.presentation.main.song

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import fr.yggz.android.lyricscollection.databinding.DialogDetailsSongBinding
import fr.yggz.android.lyricscollection.databinding.FragmentSongBinding

class SongFragment : Fragment() {

    private var _binding: FragmentSongBinding? = null
    private val _songViewModel: SongViewModel by viewModels()

    private val binding get() = _binding!!
    private lateinit var _songAdapter: SongAdapter
    private lateinit var _layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSongBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerViewSongs = binding.recyclerViewSongs
        recyclerViewSongs.adapter = _songAdapter
        _layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerViewSongs.layoutManager = _layoutManager
        recyclerViewSongs.setHasFixedSize(true)
        recyclerViewSongs.addItemDecoration(
            DividerItemDecoration(requireContext(), _layoutManager.orientation)
        )
        _songViewModel.songs.observe(viewLifecycleOwner){
            _songAdapter.songList = it
            _songAdapter.notifyDataSetChanged()
        }
        _songViewModel.error.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
        }
        _songViewModel.getSongs()
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _songAdapter = SongAdapter(emptyList())
        _songAdapter.onFavClick = { song ->
            song?.let {
                _songViewModel.updateSongFavorite(it.id, !it.favorite)
            }
        }
        _songAdapter.onItemClick = { song ->
            song?.let {
                val viewSongDetails = DialogDetailsSongBinding.inflate(LayoutInflater.from(requireContext()), binding.root as ViewGroup,false)
                viewSongDetails.song = song
                val dialog = Dialog(requireContext())
                dialog.setContentView(viewSongDetails.root)
                dialog.show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}