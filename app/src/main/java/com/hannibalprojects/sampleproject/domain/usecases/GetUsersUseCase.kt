package com.hannibalprojects.sampleproject.domain.usecases

import com.hannibalprojects.sampleproject.domain.Repository
import com.hannibalprojects.sampleproject.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(): List<User> = withContext(Dispatchers.IO) {
        repository.getUsers()
    }
}