package com.hannibalprojects.sampleproject.data.remote

import com.hannibalprojects.sampleproject.domain.User

interface RemoteDataSource {
    suspend fun getUsers() :List<User>?
}