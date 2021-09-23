package com.hannibalprojects.sampleproject.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hannibalprojects.sampleproject.domain.User
import com.hannibalprojects.sampleproject.domain.usecases.GetUsersUseCase
import com.hannibalprojects.sampleproject.domain.usecases.RefreshUsersUseCase
import com.hannibalprojects.sampleproject.presentation.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ListUsersViewModel @Inject constructor(
    private val usersUseCase: GetUsersUseCase,
    private val refreshUsersUseCase: RefreshUsersUseCase
) : ViewModel() {

    val loadUsersLiveData = MutableLiveData<DataWrapper<List<User>>>()
    fun loadUsers(){
        viewModelScope.launch {
            try {
                usersUseCase.execute().collect {
                    loadUsersLiveData.postValue(Success(it))
                }
            } catch (e: Exception) {
                loadUsersLiveData.postValue(Failure(RequestError(e)))
            }
        }
    }

    val refreshUsersLiveData = MutableLiveData<DataWrapper<Boolean>>()
    fun refreshUsers(isRefresh: Boolean) {
        viewModelScope.launch {
            refreshUsersLiveData.postValue(Loading(isRefresh))
            try {
                val data = refreshUsersUseCase.execute()
                refreshUsersLiveData.postValue(Success(data))
            } catch (e: Exception) {
                refreshUsersLiveData.postValue(Failure(RequestError(e)))
            }
            refreshUsersLiveData.postValue(Loading(false))
        }
    }
}