package com.hannibalprojects.sampleproject.presentation.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import com.hannibalprojects.sampleproject.domain.User
import com.hannibalprojects.sampleproject.domain.UsersResponse
import com.hannibalprojects.sampleproject.domain.usecases.GetUsersUseCase
import com.hannibalprojects.sampleproject.domain.usecases.RefreshUsersUseCase
import com.hannibalprojects.sampleproject.presentation.models.*

class ListUsersViewModel @ViewModelInject constructor(
    private val usersUseCase: GetUsersUseCase,
    private val refreshUsersUseCase: RefreshUsersUseCase
) : ViewModel() {

    val loadUsersLiveData: MutableLiveData<DataWrapper<DataSource.Factory<Int, User>>> by lazy {
        MutableLiveData<DataWrapper<DataSource.Factory<Int, User>>>().also {
            usersUseCase.execute {
                onComplete {
                    loadUsersLiveData.postValue(Success(it))
                }
                onError {
                    loadUsersLiveData.postValue(Failure(RequestError(it)))
                }
            }
        }
    }

    val refreshUsersLiveData = MutableLiveData<DataWrapper<Boolean>>()
    fun refreshUsers() {
        refreshUsersLiveData.value = Loading(true)
        refreshUsersUseCase.execute {
            onComplete {
                refreshUsersLiveData.value = Loading(false)
                refreshUsersLiveData.postValue(Success(it))
            }
            onError {
                refreshUsersLiveData.value = Loading(false)
                refreshUsersLiveData.postValue(Failure(RequestError(it)))
            }
        }
    }
}