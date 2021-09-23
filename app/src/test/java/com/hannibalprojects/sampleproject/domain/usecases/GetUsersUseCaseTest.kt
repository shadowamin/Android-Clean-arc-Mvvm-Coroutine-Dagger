package com.hannibalprojects.sampleproject.domain.usecases

import com.hannibalprojects.sampleproject.domain.Repository
import com.hannibalprojects.sampleproject.domain.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExperimentalCoroutinesApi
@ExtendWith(MockitoExtension::class)
class GetUsersUseCaseTest {

    @Mock
    lateinit var repository: Repository

    @InjectMocks
    lateinit var getUsersUseCase: GetUsersUseCase

    private val user = User(
        id = 3,
        email = "user@email.com",
        firstName = "firstName",
        lastName = "lastName",
        avatar = "avatar"
    )

    @Test
    fun executeTask(): Unit = runBlocking {
        val usersFlow: Flow<List<User>> = flow {
            listOf(user)
        }
        given(repository.getUsers()).willReturn(usersFlow)
        val result = getUsersUseCase.execute()
        assertThat(result).isEqualTo(usersFlow)

        then(repository).should().getUsers()
    }
}