package com.hannibalprojects.sampleproject.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.hannibalprojects.sampleproject.domain.Repository
import com.hannibalprojects.sampleproject.domain.User
import com.hannibalprojects.sampleproject.domain.UsersResponse
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val dataSource: UsersDataSource) : Repository {
    override suspend fun getUser(id : Int): LiveData<User> {
       return dataSource.getUser(id)
    }

    override suspend fun getUsers(): DataSource.Factory<Int, User> {
        return dataSource.getUsers()
    }

    override suspend fun refreshUsers(): UsersResponse {
        return dataSource.refreshUsers()
    }
    
}