package com.hannibalprojects.sampleproject.domain.usecases

import com.hannibalprojects.sampleproject.domain.Repository
import com.hannibalprojects.sampleproject.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(userId : Int): User = withContext(Dispatchers.IO) {
        repository.getUser(userId)
    }
}