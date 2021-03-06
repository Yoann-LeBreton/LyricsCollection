package fr.yggz.android.lyricscollection.presentation.main.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import fr.yggz.android.lyricscollection.databinding.FragmentAlbumBinding
import fr.yggz.android.lyricscollection.presentation.adapters.AlbumAdapter

class AlbumFragment : Fragment() {

    private var _binding: FragmentAlbumBinding? = null
    private val _albumViewModel: AlbumViewModel by viewModels()

    private val binding get() = _binding!!
    private lateinit var _albumAdapter: AlbumAdapter
    private lateinit var _layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _albumAdapter = AlbumAdapter(emptyList())
        _albumAdapter.onFavClick = { album ->
            album?.let {
                _albumViewModel.updateAlbumFavorite(it.id, !it.favorite)
            }
        }
        _albumAdapter.onItemClick = {}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerViewAlbums = binding.recyclerViewAlbums
        recyclerViewAlbums.adapter = _albumAdapter
        _layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerViewAlbums.layoutManager = _layoutManager
        recyclerViewAlbums.setHasFixedSize(true)
        recyclerViewAlbums.addItemDecoration(
            DividerItemDecoration(requireContext(), _layoutManager.orientation)
        )
        _albumViewModel.albums.observe(viewLifecycleOwner) {
            _albumAdapter.albumList = it
            _albumAdapter.notifyDataSetChanged()
        }
        _albumViewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
        }
        _albumViewModel.getAlbums()
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}