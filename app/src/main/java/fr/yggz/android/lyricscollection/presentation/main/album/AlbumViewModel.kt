package fr.yggz.android.lyricscollection.presentation.main.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.yggz.android.lyricscollection.domain.usecases.GetAlbumsUseCase
import fr.yggz.android.lyricscollection.domain.usecases.SetAlbumFavoriteUseCase
import fr.yggz.android.lyricscollection.models.ui.Album
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AlbumViewModel : ViewModel(), KoinComponent{

    private val getAlbumsUseCase: GetAlbumsUseCase by inject()
    private val setAlbumFavoriteUseCase: SetAlbumFavoriteUseCase by inject()

    private val _albums = MutableLiveData<List<Album>>().apply {
        value = emptyList()
    }

    val albums: LiveData<List<Album>> = _albums

    fun getAlbums(){
        viewModelScope.launch {
            val albumsFlow = getAlbumsUseCase.invoke()
            albumsFlow.collect{ albums ->
                _albums.postValue(albums.map {
                    Album(it.id, it.title, it.favorite, emptyList())
                })
            }
        }
    }

    fun updateAlbumFavorite(albumId: Int, favorite: Boolean){
        viewModelScope.launch {
            setAlbumFavoriteUseCase.invoke(albumId, favorite)
        }
    }
}