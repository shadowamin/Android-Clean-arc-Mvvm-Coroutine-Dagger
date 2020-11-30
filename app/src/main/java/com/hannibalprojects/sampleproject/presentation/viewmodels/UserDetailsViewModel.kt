package com.hannibalprojects.sampleproject.presentation.viewmodels

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hannibalprojects.sampleproject.domain.User
import com.hannibalprojects.sampleproject.domain.usecases.GetUserUseCase
import com.hannibalprojects.sampleproject.presentation.models.DataWrapper
import com.hannibalprojects.sampleproject.presentation.models.Failure
import com.hannibalprojects.sampleproject.presentation.models.RequestError

class UserDetailsViewModel @ViewModelInject constructor(private val getUserUseCase: GetUserUseCase) : ViewModel() {
    val observableUser = ObservableField<User>()
    val loadUsersLiveData = MutableLiveData<DataWrapper<User>>()
    fun getUserDetails(id: Int) {
        getUserUseCase.userId = id
        getUserUseCase.execute {
            onComplete {
                observableUser.set(it)
            }
            onError {
                loadUsersLiveData.postValue(Failure(RequestError(it)))
            }
        }
    }

}