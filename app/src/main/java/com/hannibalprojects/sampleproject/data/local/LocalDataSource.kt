package com.hannibalprojects.sampleproject.data.local

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val userDao: UserDao) {

    internal fun getUsers(): Flow<List<UserEntity>> = userDao.getAllLiveUsers()

    internal fun getUser(id: Int): UserEntity = userDao.getUser(id)

    internal fun insertUsers(listUsers: List<UserEntity>) = userDao.insertAllUsers(listUsers).isNotEmpty()
}