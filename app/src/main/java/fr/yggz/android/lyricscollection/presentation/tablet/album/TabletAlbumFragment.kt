package fr.yggz.android.lyricscollection.presentation.tablet.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import fr.yggz.android.lyricscollection.databinding.FragmentTabletAlbumBinding
import fr.yggz.android.lyricscollection.presentation.adapters.AlbumAdapter
import fr.yggz.android.lyricscollection.presentation.tablet.TabletViewModel

class TabletAlbumFragment : Fragment() {

    private var _binding: FragmentTabletAlbumBinding? = null
    private lateinit var _tabletViewModel: TabletViewModel
    private val binding get() = _binding!!
    private lateinit var _albumAdapter: AlbumAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _albumAdapter = AlbumAdapter(emptyList())
        _albumAdapter.onFavClick = { album ->
            album?.let {
                _tabletViewModel.updateAlbumFavorite(it.id, !it.favorite)
            }
        }
        _albumAdapter.onItemClick = { album ->
            album?.let {
                _tabletViewModel.getSongsByAlbumId(album.id)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabletAlbumBinding.inflate(inflater, container, false)
        val root: View = binding.root
        _tabletViewModel = ViewModelProvider(requireActivity())[TabletViewModel::class.java]
        val recyclerViewAlbums = binding.recyclerViewTabletAlbums
        recyclerViewAlbums.adapter = _albumAdapter
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerViewAlbums.layoutManager = layoutManager
        recyclerViewAlbums.setHasFixedSize(true)
        recyclerViewAlbums.addItemDecoration(
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        )
        _tabletViewModel.albums.observe(viewLifecycleOwner) {
            _albumAdapter.albumList = it
            _albumAdapter.notifyDataSetChanged()
        }
        _tabletViewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
        }
        _tabletViewModel.getAlbums()
        return root
    }
}