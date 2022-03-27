package fr.yggz.android.lyricscollection.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.yggz.android.lyricscollection.domain.usecases.SyncSongsUseCase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel() : ViewModel(), KoinComponent {

    private val syncSongsUseCase: SyncSongsUseCase by inject()
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun syncSongs(){
        viewModelScope.launch {
            syncSongsUseCase.invoke()
        }
    }
}