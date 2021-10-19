package com.hannibalprojects.sampleproject.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hannibalprojects.sampleproject.domain.User
import com.hannibalprojects.sampleproject.domain.usecases.GetUsersUseCase
import com.hannibalprojects.sampleproject.presentation.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListUsersViewModel @Inject constructor(
    private val usersUseCase: GetUsersUseCase
) : ViewModel() {

    val loadUsersLiveData = MutableLiveData<DataWrapper<List<User>>>()
    fun loadUsers() {
        loadUsersLiveData.value = Loading(true)
        viewModelScope.launch {
            try {
                val data = usersUseCase.execute()
                loadUsersLiveData.postValue(Success(data))
            } catch (e: Exception) {
                loadUsersLiveData.postValue(Failure(RequestError(e)))
            } finally {
                withContext(Dispatchers.Main) {
                    loadUsersLiveData.value = Loading(false)
                }
            }
        }
    }
}