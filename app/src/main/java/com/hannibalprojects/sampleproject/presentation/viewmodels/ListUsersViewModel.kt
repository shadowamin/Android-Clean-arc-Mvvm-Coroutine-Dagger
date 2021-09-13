package com.hannibalprojects.sampleproject.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import com.hannibalprojects.sampleproject.domain.User
import com.hannibalprojects.sampleproject.domain.usecases.GetUsersUseCase
import com.hannibalprojects.sampleproject.domain.usecases.RefreshUsersUseCase
import com.hannibalprojects.sampleproject.presentation.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class ListUsersViewModel @Inject constructor(
    private val usersUseCase: GetUsersUseCase,
    private val refreshUsersUseCase: RefreshUsersUseCase
) : ViewModel() {

    val loadUsersLiveData: MutableLiveData<DataWrapper<DataSource.Factory<Int, User>>> by lazy {
        MutableLiveData<DataWrapper<DataSource.Factory<Int, User>>>().also {
            usersUseCase.execute().collect {

            }
        }
    }

    val refreshUsersLiveData = MutableLiveData<DataWrapper<Boolean>>()
    fun refreshUsers(isRefresh : Boolean) {
        refreshUsersLiveData.value = Loading(isRefresh)
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