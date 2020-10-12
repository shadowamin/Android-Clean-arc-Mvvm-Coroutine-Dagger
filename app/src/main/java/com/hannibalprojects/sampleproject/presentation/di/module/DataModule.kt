package com.hannibalprojects.sampleproject.presentation.di.module

import com.hannibalprojects.sampleproject.data.RepositoryImpl
import com.hannibalprojects.sampleproject.data.UsersDataSource
import com.hannibalprojects.sampleproject.data.UsersDataSourceImpl
import com.hannibalprojects.sampleproject.data.local.LocalDataSource
import com.hannibalprojects.sampleproject.data.local.LocalDataSourceImpl
import com.hannibalprojects.sampleproject.data.remote.RemoteDataSource
import com.hannibalprojects.sampleproject.data.remote.RemoteDataSourceImpl
import com.hannibalprojects.sampleproject.domain.Repository
import com.hannibalprojects.sampleproject.presentation.di.module.ApiModule
import com.hannibalprojects.sampleproject.presentation.di.module.DaoModule
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module(includes = [DaoModule::class, ApiModule::class])
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindsRepository(repositoryImpl: RepositoryImpl): Repository

    @Binds
    abstract fun bindsDataSource(dataSourceImpl: UsersDataSourceImpl): UsersDataSource

    @Binds
    abstract fun bindsLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

    @Binds
    abstract fun bindsRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource
}