package com.hannibalprojects.sampleproject.presentation.viewmodels

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import com.hannibalprojects.sampleproject.domain.User
import com.hannibalprojects.sampleproject.domain.usecases.GetUsersUseCase
import com.hannibalprojects.sampleproject.domain.usecases.RefreshUsersUseCase

class ListUsersViewModel @ViewModelInject constructor(
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