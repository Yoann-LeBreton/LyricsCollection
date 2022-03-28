package fr.yggz.android.lyricscollection.presentation.main.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.yggz.android.lyricscollection.domain.common.ManageExceptions
import fr.yggz.android.lyricscollection.domain.usecases.GetAlbumsUseCase
import fr.yggz.android.lyricscollection.domain.usecases.SetAlbumFavoriteUseCase
import fr.yggz.android.lyricscollection.models.ui.Album
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.Exception

class AlbumViewModel : ViewModel(), KoinComponent{

    private val getAlbumsUseCase: GetAlbumsUseCase by inject()
    private val setAlbumFavoriteUseCase: SetAlbumFavoriteUseCase by inject()

    private val _error = MutableLiveData<ManageExceptions.CustomException>().apply {  }
    private val _albums = MutableLiveData<List<Album>>().apply {
        value = emptyList()
    }

    val albums: LiveData<List<Album>> = _albums
    val error: LiveData<ManageExceptions.CustomException> = _error

    fun getAlbums(){
        viewModelScope.launch {
            try {
                val albumsFlow = getAlbumsUseCase.invoke()
                albumsFlow.collect{ albums ->
                    _albums.postValue(albums.map {
                        Album(it.id, it.title, it.favorite, emptyList())
                    })
                }
            }catch (ex: Exception){
                _error.postValue(ManageExceptions.ReadLocalDBException(ex.message ?: "failed to get local data"))
            }
        }
    }

    fun updateAlbumFavorite(albumId: Int, favorite: Boolean){
        viewModelScope.launch {
            try{
                setAlbumFavoriteUseCase.invoke(albumId, favorite)
            }catch (ex: Exception){
                _error.postValue(ManageExceptions.WriteLocalDBException(ex.message ?: "failed to update local data"))
            }
        }
    }
}