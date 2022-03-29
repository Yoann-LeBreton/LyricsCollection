package fr.yggz.android.lyricscollection.domain.common

import android.util.Log

object ManageExceptions {
    abstract class CustomException(message: String) : Exception(message)

    class ServiceUnavailableException(message: String) : CustomException(message)
    class ServiceNotFoundException(message: String) : CustomException(message)
    class ServiceInternalErrorException(message: String) : CustomException(message)
    class UnauthorizedException(message: String) : CustomException(message)
    class InvalidParameterException(message: String) : CustomException(message)
    class ResponseBodyEmptyException(message: String) : CustomException(message)
    class ServiceConflictException(message: String) : CustomException(message)
    class ReadLocalDBException(message: String) : CustomException(message)
    class WriteLocalDBException(message: String) : CustomException(message)
    class UnknowException(message: String) : CustomException(message)

    fun manageNetworkError(errorCode: Int, messageError: String): CustomException {
        Log.e("Network Error", "network error $errorCode: $messageError")
        return when (errorCode) {
            NetworkCodeConstants.BAD_REQUEST -> InvalidParameterException(messageError)
            NetworkCodeConstants.FORBIDDEN -> UnauthorizedException(messageError)
            NetworkCodeConstants.NOT_FOUND -> ServiceNotFoundException(messageError)
            NetworkCodeConstants.CONFLICT -> ServiceConflictException(messageError)
            NetworkCodeConstants.INTERNAL_SERVER_ERROR -> ServiceInternalErrorException(messageError)
            NetworkCodeConstants.SERVICE_UNAIVALABLE -> ServiceUnavailableException(messageError)
            else -> UnknowException(messageError)
        }
    }

    object NetworkCodeConstants {
        const val BAD_REQUEST = 400
        const val FORBIDDEN = 403
        const val NOT_FOUND = 404
        const val CONFLICT = 409
        const val INTERNAL_SERVER_ERROR = 500
        const val SERVICE_UNAIVALABLE = 503
    }
}