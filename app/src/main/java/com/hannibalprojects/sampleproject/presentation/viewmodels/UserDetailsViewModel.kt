package com.hannibalprojects.sampleproject.presentation.viewmodels

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.hannibalprojects.sampleproject.domain.User
import com.hannibalprojects.sampleproject.domain.usecases.GetUserUseCase

class UserDetailsViewModel @ViewModelInject constructor(private val getUserUseCase: GetUserUseCase) : ViewModel(){

    val observableUser  = ObservableField<User>()

    fun getUserDetails(id : Int){
        getUserUseCase.userId=id
        getUserUseCase.execute {
                it.observeForever {
                    observableUser.set(it)
                }
        }
    }

}