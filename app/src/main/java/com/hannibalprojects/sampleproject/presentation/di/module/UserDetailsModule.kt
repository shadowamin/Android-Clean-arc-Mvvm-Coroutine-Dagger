package com.hannibalprojects.sampleproject.presentation.di.module

import androidx.lifecycle.ViewModel
import com.hannibalprojects.sampleproject.presentation.di.ViewModelKey
import com.hannibalprojects.sampleproject.presentation.di.ViewModelsModuleBuilder
import com.hannibalprojects.sampleproject.presentation.frags.UserDetailsFragment
import com.hannibalprojects.sampleproject.presentation.viewmodels.UserDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class UserDetailsModule {

    @ContributesAndroidInjector(modules = [ViewModelsModuleBuilder::class])
    internal abstract fun getUserDetailsFrag(): UserDetailsFragment

    @Binds
    @IntoMap
    @ViewModelKey(UserDetailsViewModel::class)
     abstract fun getUserDetailsViewModel(userDetailsViewModel: UserDetailsViewModel): ViewModel

}