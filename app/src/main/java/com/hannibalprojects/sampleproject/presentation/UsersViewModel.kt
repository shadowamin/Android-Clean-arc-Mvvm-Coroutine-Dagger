package com.hannibalprojects.sampleproject.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hannibalprojects.sampleproject.domain.User
import com.hannibalprojects.sampleproject.domain.usecases.GetUserUseCase
import com.hannibalprojects.sampleproject.domain.usecases.GetUsersUseCase
import com.hannibalprojects.sampleproject.domain.usecases.RefreshUsersUseCase
import com.hannibalprojects.sampleproject.presentation.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val usersUseCase: GetUsersUseCase,
    private val refreshUsersUseCase: RefreshUsersUseCase,
    private val userUseCase: GetUserUseCase
) : ViewModel() {

    val loadUserLiveData = MutableLiveData<DataWrapper<User>>()
    val loadUsersLiveData = MutableLiveData<DataWrapper<List<User>>>()
    fun loadUsers() {
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

    val refreshUsersLiveData = MutableLiveData<Boolean>()
    fun refreshUsers(isRefresh: Boolean) {
        viewModelScope.launch {
            refreshUsersLiveData.postValue(isRefresh)
            try {
                refreshUsersUseCase.execute()
                refreshUsersLiveData.postValue(false)
            } catch (e: Exception) {
                refreshUsersLiveData.postValue(false)
            }
            refreshUsersLiveData.postValue(false)
        }
    }

    fun getUserDetails(id: Int) {
        viewModelScope.launch {
            try {
                val user = userUseCase.execute(id)
                loadUserLiveData.postValue(Success(user))
            } catch (e: Exception) {
                loadUserLiveData.postValue(Failure(RequestError(e)))
            }
        }
    }
}