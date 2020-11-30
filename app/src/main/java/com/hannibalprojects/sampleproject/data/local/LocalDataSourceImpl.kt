package com.hannibalprojects.sampleproject.data.local

import androidx.paging.DataSource
import com.hannibalprojects.sampleproject.domain.User
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val userDao: UserDao) : LocalDataSource {
    override suspend fun getUsers(): DataSource.Factory<Int, User> {
        return userDao.getAllLiveUsers()
    }

    override suspend fun getUser(id: Int): User {
        return userDao.getUser(id)
    }

    override suspend fun insertUsers(listUsers: List<User>) {
        userDao.insertAllUsers(listUsers)
    }
}