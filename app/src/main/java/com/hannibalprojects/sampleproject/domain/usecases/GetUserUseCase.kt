package com.hannibalprojects.sampleproject.domain.usecases

import com.hannibalprojects.sampleproject.domain.Repository
import com.hannibalprojects.sampleproject.domain.User
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: Repository) : UseCase<User>() {
    var userId =0

    override suspend fun executeTask(): User {
        return repository.getUser(userId)
    }
}