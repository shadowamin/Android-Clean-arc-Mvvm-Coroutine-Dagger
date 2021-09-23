package com.hannibalprojects.sampleproject.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class LocalDataSourceTest {

    @Mock
    lateinit var userDao: UserDao

    @InjectMocks
    lateinit var localDataSource: LocalDataSource

    private val user = UserEntity(
        id = 3,
        email = "user@email.com",
        firstName = "firstName",
        lastName = "lastName",
        avatar = "avatar"
    )

    @Test
    fun getAllLiveUsers() {
        //Given
        val usersFlow: Flow<List<UserEntity>> = flow {
            listOf(user)
        }
        given(userDao.getAllLiveUsers()).willReturn(usersFlow)

        // When
        val result = localDataSource.getUsers()

        // Then
        then(userDao).should().getAllLiveUsers()
        assertThat(result).isEqualTo(usersFlow)
    }

    @Test
    fun getUser() {
        // Given
        given(userDao.getUser(3)).willReturn(user)

        // When
        val result = localDataSource.getUser(3)

        // Then
        then(userDao).should().getUser(3)
        assertThat(result).isEqualTo(user)
    }

    @Test
    fun insertUsers() {
        // Given
        val usersList = listOf(user)
        given(userDao.insertAllUsers(usersList)).willReturn(listOf(1L))

        // When
        val result = localDataSource.insertUsers(usersList)

        // Then
        then(userDao).should().insertAllUsers(usersList)
        assertThat(result).isEqualTo(true)
    }

}