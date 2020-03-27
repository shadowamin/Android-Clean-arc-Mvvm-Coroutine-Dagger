package com.hannibalprojects.sampleproject.presentation.di

import android.content.Context
import com.hannibalprojects.sampleproject.SampleApplication
import com.hannibalprojects.sampleproject.presentation.DownloadWorker
import com.hannibalprojects.sampleproject.presentation.di.module.DataModule
import com.hannibalprojects.sampleproject.presentation.di.module.UserDetailsModule
import com.hannibalprojects.sampleproject.presentation.di.module.UsersListModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [DataModule::class,
        UsersListModule::class,
        UserDetailsModule::class,
        AndroidSupportInjectionModule::class]
)
interface AppComponent : AndroidInjector<SampleApplication> {

    fun inject(worker: DownloadWorker)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}