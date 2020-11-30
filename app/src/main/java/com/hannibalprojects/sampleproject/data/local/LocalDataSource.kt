package com.hannibalprojects.sampleproject.data.local

import androidx.paging.DataSource
import com.hannibalprojects.sampleproject.domain.User

interface LocalDataSource {
    suspend fun getUsers(): DataSource.Factory<Int, User>

    suspend fun getUser(id: Int): User

    suspend fun insertUsers(listUsers: List<User>)
}