package com.hannibalprojects.sampleproject.presentation.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.hannibalprojects.sampleproject.domain.User
import com.hannibalprojects.sampleproject.domain.usecases.GetUserUseCase
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class UserDetailsViewModel @Inject constructor(private val getUserUseCase: GetUserUseCase) : ViewModel(){

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