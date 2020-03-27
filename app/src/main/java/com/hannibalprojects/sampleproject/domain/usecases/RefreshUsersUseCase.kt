package com.hannibalprojects.sampleproject.domain.usecases

import com.hannibalprojects.sampleproject.domain.Repository
import com.hannibalprojects.sampleproject.domain.UsersResponse
import javax.inject.Inject

class RefreshUsersUseCase @Inject constructor(private val repository: Repository) : UseCase<UsersResponse>() {

    override suspend fun executeTask(): UsersResponse {
        return repository.refreshUsers()
    }
}