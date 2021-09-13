package com.hannibalprojects.sampleproject.data

import com.hannibalprojects.sampleproject.domain.User
import kotlinx.coroutines.flow.Flow

interface UsersDataSource {
    fun getUsers() : Flow<List<User>>

    suspend fun getUser(id : Int) : User

    suspend fun refreshUsers() : Boolean
}