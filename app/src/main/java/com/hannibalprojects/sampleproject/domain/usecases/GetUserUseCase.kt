package com.hannibalprojects.sampleproject.domain.usecases

import androidx.lifecycle.LiveData
import com.hannibalprojects.sampleproject.domain.Repository
import com.hannibalprojects.sampleproject.domain.User
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: Repository) : UseCase<LiveData<User>>() {
    var userId =0

    override suspend fun executeTask(): LiveData<User> {
        return repository.getUser(userId)
    }
}