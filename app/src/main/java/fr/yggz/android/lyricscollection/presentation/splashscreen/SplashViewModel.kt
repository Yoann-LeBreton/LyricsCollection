package fr.yggz.android.lyricscollection.presentation.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.yggz.android.lyricscollection.domain.common.Result
import fr.yggz.android.lyricscollection.domain.common.StateDataLiveData
import fr.yggz.android.lyricscollection.domain.usecases.SyncSongsUseCase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SplashViewModel : ViewModel(), KoinComponent {
    private val syncSongsUseCase: SyncSongsUseCase by inject()
    val syncResultState = StateDataLiveData<String?>()

    fun syncSongs() {
        viewModelScope.launch {
            syncResultState.postLoading()
            when (val result = syncSongsUseCase.invoke()) {
                is Result.Success -> {
                    syncResultState.postSuccess(result.data)
                }
                is Result.Error -> {
                    syncResultState.postError(result.exception)
                }
            }
        }
    }
}