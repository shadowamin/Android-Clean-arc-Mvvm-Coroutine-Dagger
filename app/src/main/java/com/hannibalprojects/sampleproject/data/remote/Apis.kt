package com.hannibalprojects.sampleproject.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface UserApi {
    @GET("/api/users")
    suspend fun getUsers(): Response<WsUsers>
}