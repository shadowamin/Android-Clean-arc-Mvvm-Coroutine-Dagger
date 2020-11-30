package com.hannibalprojects.sampleproject.domain

import androidx.paging.DataSource

interface Repository {

    suspend fun getUsers(): DataSource.Factory<Int, User>

    suspend fun getUser(id : Int): User

    suspend fun refreshUsers(): UsersResponse
}