package fr.yggz.android.lyricscollection.presentation.tablet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.yggz.android.lyricscollection.domain.common.ManageExceptions
import fr.yggz.android.lyricscollection.domain.usecases.GetAlbumsUseCase
import fr.yggz.android.lyricscollection.domain.usecases.GetSongsByAlbumIdUseCase
import fr.yggz.android.lyricscollection.domain.usecases.SetAlbumFavoriteUseCase
import fr.yggz.android.lyricscollection.domain.usecases.SetSongFavoriteUseCase
import fr.yggz.android.lyricscollection.models.ui.Album
import fr.yggz.android.lyricscollection.models.ui.Song
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TabletViewModel : ViewModel(), KoinComponent {

    private val getAlbumsUseCase: GetAlbumsUseCase by inject()
    private val getSongsByAlbumIdUseCase: GetSongsByAlbumIdUseCase by inject()
    private val setAlbumFavoriteUseCase: SetAlbumFavoriteUseCase by inject()
    private val setSongFavoriteUseCase: SetSongFavoriteUseCase by inject()

    private val _error = MutableLiveData<ManageExceptions.CustomException>().apply { }
    private val _songs = MutableLiveData<List<Song>>().apply {
        value = emptyList()
    }
    private val _albums = MutableLiveData<List<Album>>().apply {
        value = emptyList()
    }
    val songs: LiveData<List<Song>> = _songs
    val albums: LiveData<List<Album>> = _albums
    val error: LiveData<ManageExceptions.CustomException> = _error

    fun getAlbums() {
        viewModelScope.launch {
            try {
                val albumsFlow = getAlbumsUseCase.invoke()
                albumsFlow.collect { albums ->
                    _albums.postValue(albums.map {
                        Album(it.id, it.title, it.favorite, emptyList())
                    })
                }
            } catch (ex: Exception) {
                _error.postValue(
                    ManageExceptions.ReadLocalDBException(
                        ex.message ?: "failed to get local data"
                    )
                )
            }
        }
    }

    fun getSongsByAlbumId(albumId: Int) {
        viewModelScope.launch {
            try {
                val songs = getSongsByAlbumIdUseCase.invoke(albumId).first()
                val songsUI = songs.map {
                    Song(it.id, it.title, it.favorite, it.pictureUrl, it.thumbnailUrl)
                }
                _songs.postValue(songsUI)
            } catch (ex: Exception) {
                _error.postValue(
                    ManageExceptions.ReadLocalDBException(
                        ex.message ?: "failed to get local data"
                    )
                )
            }
        }
    }

    fun updateAlbumFavorite(albumId: Int, favorite: Boolean) {
        viewModelScope.launch {
            try {
                setAlbumFavoriteUseCase.invoke(albumId, favorite)
            } catch (ex: Exception) {
                _error.postValue(
                    ManageExceptions.WriteLocalDBException(
                        ex.message ?: "failed to update local data"
                    )
                )
            }
        }
    }

    fun updateSongFavorite(songId: Int, favorite: Boolean) {
        viewModelScope.launch {
            try {
                setSongFavoriteUseCase.invoke(songId, favorite)
            } catch (ex: Exception) {
                _error.postValue(
                    ManageExceptions.WriteLocalDBException(
                        ex.message ?: "failed to update local data"
                    )
                )
            }
        }
    }
}