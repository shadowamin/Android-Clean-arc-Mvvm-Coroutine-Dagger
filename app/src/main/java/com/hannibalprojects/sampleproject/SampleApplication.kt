package com.hannibalprojects.sampleproject

import com.hannibalprojects.sampleproject.presentation.di.DaggerAppComponent
import com.hannibalprojects.sampleproject.presentation.di.Provider
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class SampleApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.factory().create(applicationContext)
        Provider.appComponent = appComponent
        return appComponent
    }

}