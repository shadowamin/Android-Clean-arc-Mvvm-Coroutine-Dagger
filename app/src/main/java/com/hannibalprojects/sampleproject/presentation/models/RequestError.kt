package com.hannibalprojects.sampleproject.presentation.models

import retrofit2.HttpException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

data class RequestError(
    var errorType: ErrorType = ErrorType.UNKNOWN_ERROR,
    var code: String = "",
    var message: String = ""
) {
    constructor(throwable: Throwable) : this() {
        when (throwable) {
            is UnknownHostException -> {
                this.errorType = ErrorType.NO_CONNECTIVITY_ERROR
            }

            // Timeout
            is TimeoutException -> {
                this.errorType = ErrorType.TIMEOUT_ERROR
            }
            // Server
            is HttpException -> {
                this.errorType = ErrorType.SERVER_ERROR
                throwable.response()?.errorBody()?.string()?.let { _ ->
                    this.code = throwable.response()?.code()?.toString().orEmpty()
                    this.message = throwable.response()?.message().orEmpty()
                }
            }

            else -> {
                this.errorType = ErrorType.UNKNOWN_ERROR
            }
        }
    }
}

enum class ErrorType {
    UNKNOWN_ERROR,
    TIMEOUT_ERROR, // Timeout
    SERVER_ERROR,
    NO_CONNECTIVITY_ERROR, // No connectivity
}