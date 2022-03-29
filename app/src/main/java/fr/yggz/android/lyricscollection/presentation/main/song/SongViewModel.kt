package fr.yggz.android.lyricscollection.presentation.main.song

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.yggz.android.lyricscollection.domain.common.ManageExceptions
import fr.yggz.android.lyricscollection.domain.usecases.GetSongsUseCase
import fr.yggz.android.lyricscollection.domain.usecases.SetSongFavoriteUseCase
import fr.yggz.android.lyricscollection.models.ui.Song
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SongViewModel : ViewModel(), KoinComponent {

    private val getSongsUseCase: GetSongsUseCase by inject()
    private val setSongFavoriteUseCase: SetSongFavoriteUseCase by inject()

    private val _error = MutableLiveData<ManageExceptions.CustomException>().apply { }
    private val _songs = MutableLiveData<List<Song>>().apply {
        value = emptyList()
    }
    val songs: LiveData<List<Song>> = _songs
    val error: LiveData<ManageExceptions.CustomException> = _error

    fun getSongs() {
        viewModelScope.launch {
            try {
                val songsFlow = getSongsUseCase.invoke()
                songsFlow.collect { songs ->
                    _songs.postValue(
                        songs.map {
                            Song(it.id, it.title, it.favorite, it.pictureUrl, it.thumbnailUrl)
                        }
                    )
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