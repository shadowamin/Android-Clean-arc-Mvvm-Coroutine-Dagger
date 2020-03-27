package com.hannibalprojects.sampleproject.presentation.viewmodels


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import com.hannibalprojects.sampleproject.domain.User
import com.hannibalprojects.sampleproject.domain.usecases.GetUsersUseCase
import com.hannibalprojects.sampleproject.domain.usecases.RefreshUsersUseCase
import com.hannibalprojects.sampleproject.domain.usecases.UseCase
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ListUsersViewModel @Inject constructor(
    private val usersUseCase: GetUsersUseCase,
    private val refreshUsersUseCase: RefreshUsersUseCase
) : ViewModel() {

    fun loadUsers(block: (DataSource.Factory<Int, User>) -> Unit) {
        usersUseCase.execute(block)
    }

    fun refreshUsers() {
        refreshUsersUseCase.execute {
            Log.i("ListUsersViewModel", "code =${it.code} message =${it.message}")
        }
    }


}