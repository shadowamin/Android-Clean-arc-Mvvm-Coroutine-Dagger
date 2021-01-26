package com.hannibalprojects.sampleproject.data

import androidx.paging.DataSource
import com.hannibalprojects.sampleproject.domain.User
import com.hannibalprojects.sampleproject.domain.UsersResponse

interface UsersDataSource {
   suspend fun getUsers() : DataSource.Factory<Int, User>

    suspend fun getUser(id : Int) : User

    suspend fun refreshUsers() : Boolean
}