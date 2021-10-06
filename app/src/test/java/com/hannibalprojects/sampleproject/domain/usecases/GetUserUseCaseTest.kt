package com.hannibalprojects.sampleproject.domain.usecases

import com.hannibalprojects.sampleproject.domain.Repository
import com.hannibalprojects.sampleproject.domain.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class GetUserUseCaseTest {

    @Mock
    lateinit var repository: Repository

    @InjectMocks
    lateinit var getUserUseCase: GetUserUseCase

    private val user = User(
        id = 3,
        email = "user@email.com",
        firstName = "firstName",
        lastName = "lastName",
        avatar = "avatar"
    )

    @Test
    fun executeTask(): Unit = runBlocking {
        // Given
        given(repository.getUser(2)).willReturn(user)

        // When
        val result = getUserUseCase.execute(2)

        // Then
        assertThat(result).isEqualTo(user)
        then(repository).should().getUser(2)
    }
}