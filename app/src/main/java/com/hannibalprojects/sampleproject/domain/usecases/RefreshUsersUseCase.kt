package com.hannibalprojects.sampleproject.domain.usecases

import com.hannibalprojects.sampleproject.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RefreshUsersUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(): Boolean = withContext(Dispatchers.IO) {
        repository.refreshUsers()
    }
}