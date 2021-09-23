package com.hannibalprojects.sampleproject.presentation.di.module

import com.hannibalprojects.sampleproject.data.RepositoryImpl
import com.hannibalprojects.sampleproject.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class DataModule {

    @Binds
    abstract fun bindsRepository(repositoryImpl: RepositoryImpl): Repository
}