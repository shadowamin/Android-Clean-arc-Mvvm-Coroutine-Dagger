package com.hannibalprojects.sampleproject.presentation.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hannibalprojects.sampleproject.domain.User
import com.hannibalprojects.sampleproject.domain.usecases.GetUserUseCase
import com.hannibalprojects.sampleproject.presentation.models.DataWrapper
import com.hannibalprojects.sampleproject.presentation.models.Failure
import com.hannibalprojects.sampleproject.presentation.models.RequestError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(private val getUserUseCase: GetUserUseCase) : ViewModel() {
    val observableUser = ObservableField<User>()
    val loadUsersLiveData = MutableLiveData<DataWrapper<User>>()

    fun getUserDetails(id: Int) {
        viewModelScope.launch {
            try {
                val user = getUserUseCase.execute(id)
                observableUser.set(user)
            } catch (e: Exception) {
                loadUsersLiveData.postValue(Failure(RequestError(e)))
            }
        }
    }

}