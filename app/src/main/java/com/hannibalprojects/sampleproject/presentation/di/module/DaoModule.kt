package com.hannibalprojects.sampleproject.presentation.di.module

import android.content.Context
import androidx.room.Room
import com.hannibalprojects.sampleproject.data.local.AppDataBase
import com.hannibalprojects.sampleproject.data.local.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DaoModule {

    @Provides
    @Singleton
    fun provideRoomDataBase(context: Context): AppDataBase {
         return Room.databaseBuilder(context,
             AppDataBase::class.java, "users.db")
            .build()
    }

    @Provides
    fun providesUsersDao(appDataBase: AppDataBase): UserDao {
        return appDataBase.getUsersDao()
    }
}