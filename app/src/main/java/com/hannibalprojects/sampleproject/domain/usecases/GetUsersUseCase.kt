package com.hannibalprojects.sampleproject.domain.usecases

import com.hannibalprojects.sampleproject.domain.Repository
import com.hannibalprojects.sampleproject.domain.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

 class GetUsersUseCase @Inject constructor(private val repository: Repository) {
     fun execute(): Flow<List<User>> {
        return repository.getUsers()
    }
}