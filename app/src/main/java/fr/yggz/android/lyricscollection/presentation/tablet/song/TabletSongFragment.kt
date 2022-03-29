package fr.yggz.android.lyricscollection.presentation.tablet.song

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import fr.yggz.android.lyricscollection.databinding.DialogDetailsSongBinding
import fr.yggz.android.lyricscollection.databinding.FragmentTabletSongBinding
import fr.yggz.android.lyricscollection.presentation.adapters.SongAdapter
import fr.yggz.android.lyricscollection.presentation.tablet.TabletViewModel

class TabletSongFragment : Fragment() {
    private var _binding: FragmentTabletSongBinding? = null
    private lateinit var _tabletViewModel: TabletViewModel
    private val binding get() = _binding!!
    private lateinit var _songAdapter: SongAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _songAdapter = SongAdapter(emptyList())
        _songAdapter.onFavClick = { song ->
            song?.let {
                _tabletViewModel.updateSongFavorite(it.id, !it.favorite)
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabletSongBinding.inflate(inflater, container, false)
        val root: View = binding.root
        _tabletViewModel = ViewModelProvider(requireActivity())[TabletViewModel::class.java]
        val recyclerViewSongs = binding.recyclerViewTabletSongs
        recyclerViewSongs.adapter = _songAdapter
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerViewSongs.layoutManager = layoutManager
        recyclerViewSongs.setHasFixedSize(true)
        recyclerViewSongs.addItemDecoration(
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        )
        _tabletViewModel.songs.observe(viewLifecycleOwner) {
            _songAdapter.songList = it
            _songAdapter.notifyDataSetChanged()
        }
        _tabletViewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}