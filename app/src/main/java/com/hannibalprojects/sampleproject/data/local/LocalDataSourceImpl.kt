package com.hannibalprojects.sampleproject.data.local

import com.hannibalprojects.sampleproject.domain.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val userDao: UserDao) : LocalDataSource {
    override fun getUsers(): Flow<List<User>> {
        return userDao.getAllLiveUsers()
    }

    override suspend fun getUser(id: Int): User {
        return userDao.getUser(id)
    }

    override suspend fun insertUsers(listUsers: List<User>) : Boolean {
      return  userDao.insertAllUsers(listUsers).isNotEmpty()
    }
}