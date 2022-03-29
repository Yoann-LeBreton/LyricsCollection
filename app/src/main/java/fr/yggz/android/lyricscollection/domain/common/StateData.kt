package fr.yggz.android.lyricscollection.domain.common

data class StateData<out T>(val status: Status, val data: T?, val throwable: Throwable?) {

    companion object {
        fun <T> success(data: T?): StateData<T> {
            return StateData(Status.SUCCESS, data, null)
        }

        fun <T> error(throwable: Throwable): StateData<T> {
            return StateData(Status.ERROR, null, throwable)
        }

        fun <T> loading(): StateData<T> {
            return StateData(Status.LOADING, null, null)
        }

        fun <T> none(): StateData<T> {
            return StateData(Status.NONE, null, null)
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
        NONE
    }
}