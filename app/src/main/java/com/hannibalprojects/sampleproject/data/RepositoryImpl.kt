package com.hannibalprojects.sampleproject.data

import com.hannibalprojects.sampleproject.data.Mapper.Companion.toDomainUser
import com.hannibalprojects.sampleproject.data.remote.RemoteDataSource
import com.hannibalprojects.sampleproject.domain.Repository
import com.hannibalprojects.sampleproject.domain.User
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : Repository {

    override suspend fun getUsers(): List<User> =
        remoteDataSource.getUsers()?.map { it.toDomainUser() } ?: emptyList()

    override suspend fun getUser(id: Int): User? =
        remoteDataSource.getUsers()?.map { it.toDomainUser() }?.firstOrNull { it.id == id }

}