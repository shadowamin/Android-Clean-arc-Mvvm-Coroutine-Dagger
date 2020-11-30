package com.hannibalprojects.sampleproject.data.local

import androidx.paging.DataSource
import androidx.room.*
import com.hannibalprojects.sampleproject.domain.User

@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getAllLiveUsers(): DataSource.Factory<Int, User>

    @Query("SELECT count(*) FROM User ")
    fun countUsers(): Int

    @Query("SELECT * FROM User")
    fun getAllUsers(): DataSource.Factory<Int, User>

    @Query("Select * From User Where id=:id")
    fun getUser(id: Int): User

    @Query("Delete From User")
    fun deleteAllUsers()

    @Delete
    fun deleteUser(user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllUsers(users: List<User>): List<Long>

}