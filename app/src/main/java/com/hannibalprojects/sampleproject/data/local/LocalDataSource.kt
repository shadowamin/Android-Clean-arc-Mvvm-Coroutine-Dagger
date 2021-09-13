package com.hannibalprojects.sampleproject.data.local

import com.hannibalprojects.sampleproject.domain.User
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getUsers(): Flow<List<User>>

    suspend fun getUser(id: Int): User

    suspend fun insertUsers(listUsers: List<User>) : Boolean
}