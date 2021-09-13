package com.hannibalprojects.sampleproject.data

import com.hannibalprojects.sampleproject.data.local.LocalDataSource
import com.hannibalprojects.sampleproject.data.remote.RemoteDataSource
import com.hannibalprojects.sampleproject.domain.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersDataSourceImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : UsersDataSource {
    override suspend fun getUser(id: Int): User {
        return localDataSource.getUser(id)
    }

    override fun getUsers(): Flow<List<User>> {
        return localDataSource.getUsers()
    }

    override suspend fun refreshUsers(): Boolean {
        val remoteList = remoteDataSource.getUsers() ?: emptyList()
        return localDataSource.insertUsers(remoteList)
    }

}