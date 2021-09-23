package com.hannibalprojects.sampleproject.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM UserEntity")
    fun getAllLiveUsers(): Flow<List<UserEntity>>

    @Query("Select * From UserEntity Where id=:id")
    fun getUser(id: Int): UserEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllUsers(users: List<UserEntity>): List<Long>

}