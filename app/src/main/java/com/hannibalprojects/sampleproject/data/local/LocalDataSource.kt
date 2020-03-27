package com.hannibalprojects.sampleproject.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.hannibalprojects.sampleproject.domain.User

interface LocalDataSource {
    suspend fun getUsers(): DataSource.Factory<Int, User>

    suspend fun getUser(id: Int): LiveData<User>

    suspend fun insertUsers(listUsers: List<User>)
}