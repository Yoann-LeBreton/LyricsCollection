package fr.yggz.android.lyricscollection.presentation.main.song

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.yggz.android.lyricscollection.domain.usecases.GetSongsUseCase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SongViewModel : ViewModel(), KoinComponent {

    private val getSongsUseCase: GetSongsUseCase by inject()

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    fun getSongs(){
        viewModelScope.launch {
            val songsFlow = getSongsUseCase.invoke()
            songsFlow.collect{ songs ->
                songs.map {
                    print(it.title)
                }
            }
        }

    }
}