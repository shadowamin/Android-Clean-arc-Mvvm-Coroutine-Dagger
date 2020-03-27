package com.hannibalprojects.sampleproject.data


import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.hannibalprojects.sampleproject.data.local.LocalDataSource
import com.hannibalprojects.sampleproject.data.remote.RemoteDataSource
import com.hannibalprojects.sampleproject.domain.User
import com.hannibalprojects.sampleproject.domain.UsersResponse
import javax.inject.Inject

class UsersDataSourceImpl @Inject constructor(
    val localDataSource: LocalDataSource,
    val remoteDataSource: RemoteDataSource
) :
    UsersDataSource {
    override suspend fun getUser(id: Int): LiveData<User> {
        return localDataSource.getUser(id)
    }

    override suspend fun getUsers(): DataSource.Factory<Int, User> {
        return localDataSource.getUsers()
    }

    override suspend fun refreshUsers(): UsersResponse {
        val remoteUsers = remoteDataSource.getUsers()
        return if (remoteUsers != null) {
            localDataSource.insertUsers(remoteUsers)
            UsersResponse(200, "")
        } else {
            UsersResponse(500, "Refresh failed")
        }
    }


}