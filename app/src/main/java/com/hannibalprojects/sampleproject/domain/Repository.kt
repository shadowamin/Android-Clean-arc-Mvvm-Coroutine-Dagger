package com.hannibalprojects.sampleproject.domain

interface Repository {

    suspend fun getUsers(): List<User>

    suspend fun getUser(id: Int): User?

}