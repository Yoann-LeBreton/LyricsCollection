package fr.yggz.android.lyricscollection.domain.common

import androidx.lifecycle.MutableLiveData

class StateDataLiveData<T> : MutableLiveData<StateData<T>>() {
    fun postLoading() {
        postValue(StateData.loading())
    }

    fun postSuccess(data: T) {
        postValue(StateData.success(data))
    }

    fun postError(throwable: Throwable) {
        postValue(StateData.error(throwable))
    }

    fun postNone() {
        postValue(StateData.none())
    }
}