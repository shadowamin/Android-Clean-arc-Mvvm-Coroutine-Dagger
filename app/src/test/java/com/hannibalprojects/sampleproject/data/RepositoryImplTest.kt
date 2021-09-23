package com.hannibalprojects.sampleproject.data

import com.hannibalprojects.sampleproject.data.local.LocalDataSource
import com.hannibalprojects.sampleproject.data.local.UserEntity
import com.hannibalprojects.sampleproject.data.remote.RemoteDataSource
import com.hannibalprojects.sampleproject.data.remote.WsUser
import com.hannibalprojects.sampleproject.domain.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
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
class RepositoryImplTest {

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @Mock
    lateinit var localDataSource: LocalDataSource

    @InjectMocks
    lateinit var repository: RepositoryImpl

    private val user = User(
        id = 3,
        email = "user@email.com",
        firstName = "firstName",
        lastName = "lastName",
        avatar = "avatar"
    )

    private val userEntity = UserEntity(
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
    fun getUser() {
        // Given
        given(localDataSource.getUser(2)).willReturn(userEntity)

        // When
        val result = repository.getUser(2)

        // Then
        then(localDataSource).should().getUser(2)
        assertThat(result).isEqualTo(user)
    }

    @Test
    fun getUsers() = runBlockingTest {
        // Given
        val usersEntityFlow: Flow<List<UserEntity>> = flow {
            listOf(userEntity)
        }
        given(localDataSource.getUsers()).willReturn(usersEntityFlow)

        // When
        val result = repository.getUsers()

        // Then
        then(localDataSource).should().getUsers()
        result.collect {
            assertThat(it).isEqualTo(listOf(user))
        }
    }


    @Test
    fun refreshUsers() = runBlockingTest {
        val users = listOf(userEntity)
        val wsUsers = listOf(userWs)
        given(remoteDataSource.getUsers()).willReturn(wsUsers)
        given(localDataSource.insertUsers(users)).willReturn(true)

        //When
        val result = repository.refreshUsers()

        //Then
        then(remoteDataSource).should().getUsers()
        then(localDataSource).should().insertUsers(users)
        assertThat(result).isTrue()

    }
}