package com.hannibalprojects.sampleproject.domain.usecases

import androidx.paging.DataSource
import com.hannibalprojects.sampleproject.domain.Repository
import com.hannibalprojects.sampleproject.domain.User
import javax.inject.Inject


 class GetUsersUseCase @Inject constructor(private val repository: Repository) :
    UseCase<DataSource.Factory<Int, User>>() {
    override suspend fun executeTask(): DataSource.Factory<Int, User> {
        return repository.getUsers()
    }
}