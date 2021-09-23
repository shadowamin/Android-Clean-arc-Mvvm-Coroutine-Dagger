package com.hannibalprojects.sampleproject.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val userApi: UserApi) {

    internal suspend fun getUsers(): List<WsUser>? {
        val wsResponse = userApi.getUsers()
        return if (wsResponse.isSuccessful) {
            wsResponse.body()?.data
        } else {
            null
        }
    }
}