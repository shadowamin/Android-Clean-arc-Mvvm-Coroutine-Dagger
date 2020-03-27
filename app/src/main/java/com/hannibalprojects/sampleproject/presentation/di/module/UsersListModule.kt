package com.hannibalprojects.sampleproject.presentation.di.module

import androidx.lifecycle.ViewModel
import com.hannibalprojects.sampleproject.presentation.di.ViewModelKey
import com.hannibalprojects.sampleproject.presentation.di.ViewModelsModuleBuilder
import com.hannibalprojects.sampleproject.presentation.frags.ListUsersFragment
import com.hannibalprojects.sampleproject.presentation.viewmodels.ListUsersViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class UsersListModule {

    @ContributesAndroidInjector(modules = [ViewModelsModuleBuilder::class])
    internal abstract fun getUsersListFragment() : ListUsersFragment


    @Binds
    @IntoMap
    @ViewModelKey(ListUsersViewModel::class)
    internal abstract fun binListUsersViewModel(usersViewModel: ListUsersViewModel) : ViewModel
}