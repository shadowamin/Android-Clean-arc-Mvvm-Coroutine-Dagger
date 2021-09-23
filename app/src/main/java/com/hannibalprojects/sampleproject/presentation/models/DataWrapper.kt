package com.hannibalprojects.sampleproject.presentation.models

sealed class DataWrapper<out T>
data class Loading<out T>(val loading: Boolean) : DataWrapper<T>()
data class Success<out T>(val data: T) : DataWrapper<T>()
data class Failure<out T>(val error : RequestError) : DataWrapper<T>()
