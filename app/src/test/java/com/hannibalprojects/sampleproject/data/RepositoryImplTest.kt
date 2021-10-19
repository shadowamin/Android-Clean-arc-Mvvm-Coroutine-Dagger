package com.hannibalprojects.sampleproject.data

import com.hannibalprojects.sampleproject.data.remote.RemoteDataSource
import com.hannibalprojects.sampleproject.data.remote.WsUser
import com.hannibalprojects.sampleproject.domain.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExperimentalCoroutinesApi
@ExtendWith(MockitoExtension::class)
class RepositoryImplTest {

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @InjectMocks
    lateinit var repository: RepositoryImpl

    private val user = User(
        id = 3,
        email = "user@email.com",
        firstName = "firstName",
        lastName = "lastName",
        avatar = "avatar"
    )

    private val userWs = WsUser(
        id = 3,
        email = "user@email.com",
        firstName = "firstName",
        lastName = "lastName",
        avatar = "avatar"
    )

    @Test
    fun `getUser -  user id existe`() = runBlockingTest {
        // Given
        val users = listOf(userWs)
        given(remoteDataSource.getUsers()).willReturn(users)

        // When
        val result = repository.getUser(3)

        // Then
        assertThat(result).isEqualTo(user)
    }

    @Test
    fun `getUser -  user id not exist`() = runBlockingTest {
        // Given
        val users = listOf(userWs)
        given(remoteDataSource.getUsers()).willReturn(users)

        // When
        val result = repository.getUser(2)

        // Then
        assertThat(result).isNull()
    }

    @Test
    fun getUsers() = runBlockingTest {
        // Given
        given(remoteDataSource.getUsers()).willReturn(listOf(userWs))

        // When
        val result = repository.getUsers()

        // Then
        assertThat(result).isEqualTo(listOf(user))

    }

}