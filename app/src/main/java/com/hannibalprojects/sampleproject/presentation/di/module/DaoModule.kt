package com.hannibalprojects.sampleproject.presentation.di.module

import android.content.Context
import androidx.room.Room
import com.hannibalprojects.sampleproject.data.local.AppDataBase
import com.hannibalprojects.sampleproject.data.local.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DaoModule {

    private const val DB_NAME = "users.db"

    @Provides
    @Singleton
    fun provideRoomDataBase(@ApplicationContext context: Context): AppDataBase =
        Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            DB_NAME
        ).build()


    @Provides
    fun providesUsersDao(appDataBase: AppDataBase): UserDao = appDataBase.getUsersDao()

}