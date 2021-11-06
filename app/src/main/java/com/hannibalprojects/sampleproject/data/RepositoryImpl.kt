package com.hannibalprojects.sampleproject.data

import com.hannibalprojects.sampleproject.data.Mapper.Companion.toDomainUser
import com.hannibalprojects.sampleproject.data.Mapper.Companion.toEntity
import com.hannibalprojects.sampleproject.data.local.LocalDataSource
import com.hannibalprojects.sampleproject.data.remote.RemoteDataSource
import com.hannibalprojects.sampleproject.domain.Repository
import com.hannibalprojects.sampleproject.domain.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : Repository {

    override fun getUsers(): Flow<List<User>> =
        localDataSource.getUsers().map {
            it.map { userEntity -> userEntity.toDomainUser() }
        }

    override fun getUser(id: Int): User = localDataSource.getUser(id).toDomainUser()

    override suspend fun refreshUsers(): Boolean {
        val remoteList = remoteDataSource.getUsers() ?: emptyList()
        return localDataSource.insertUsers(remoteList.map { it.toEntity() })
    }
}