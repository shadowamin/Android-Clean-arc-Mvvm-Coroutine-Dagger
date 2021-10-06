package com.hannibalprojects.sampleproject.data.remote

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import retrofit2.Response
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
@ExtendWith(MockitoExtension::class)
class RemoteDataSourceTest {

    @Mock
    lateinit var userApi: UserApi

    @InjectMocks
    lateinit var remoteDataSource: RemoteDataSource

    @Test
    fun `getUsers - when response throws exception - then should return exception`() = runBlockingTest {
        // Given
        given(userApi.getUsers()).willThrow(mock(RuntimeException::class.java))

        // When
        try {
            remoteDataSource.getUsers()
        } catch (e: Exception) {
            assertThat(e).isInstanceOf(RuntimeException::class.java)
        }
    }

    @Test
    fun `getUsers - when response is success - then should return a list of users`() = runBlockingTest {
        // Given
        val users = listOf(
            WsUser(
                id = 3,
                email = "user@email.com",
                firstName = "firstName",
                lastName = "lastName",
                avatar = "avatar"
            )
        )
        given(userApi.getUsers()).willReturn(
            Response.success(
                WsUsers(
                    page = 1,
                    data = users
                )
            )
        )

        // When
        val result = remoteDataSource.getUsers()

        // Then
        then(userApi).should().getUsers()
        assertThat(result).isEqualTo(users)
    }

    @Test
    fun `getUsers - when response is failure - then should return null`() = runBlockingTest {
        // Given
        given(userApi.getUsers()).willReturn(
            Response.error(404, "content".toResponseBody(" charset=utf-8".toMediaTypeOrNull()))
        )

        // When
        val result = remoteDataSource.getUsers()

        // Then
        then(userApi).should().getUsers()
        assertThat(result).isNull()
    }

}