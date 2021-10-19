package com.hannibalprojects.sampleproject.data.remote

import com.hannibalprojects.sampleproject.data.remote.Utils.enqueueWithCode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
@ExtendWith(MockitoExtension::class)
class UserApiTest {

    private val mockWebServer = MockWebServer()

    private val api: UserApi = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(UserApi::class.java)


    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `when response from mockWebservice - then should return right body`() {
        // Given
        mockWebServer.enqueueWithCode("users.json", 200)

        val expected = WsUsers(
            page = 1,
            data = listOf(
                WsUser(
                    id = 1,
                    email = "george.bluth@reqres.in",
                    firstName = "George",
                    lastName = "Bluth",
                    avatar = "https://reqres.in/img/faces/1-image.jpg"
                ),
                WsUser(
                    id = 2,
                    email = "janet.weaver@reqres.in",
                    firstName = "Janet",
                    lastName = "Weaver",
                    avatar = "https://reqres.in/img/faces/2-image.jpg"
                )
            )
        )

        runBlocking {
            // When
            val apiResponse = api.getUsers()

            // Then
            assertThat(apiResponse.isSuccessful).isTrue
            assertThat(apiResponse.body()).isEqualTo(expected)
        }
    }

    @Test
    fun `when response from mockWebservice is failure - then should return Failure`() {
        //Given
        mockWebServer.enqueueWithCode("", 400)
        runBlocking {
            // When
            val apiResponse = api.getUsers()

            // Then
            assertThat(apiResponse.isSuccessful).isFalse
        }
    }

    @Test
    fun `when response with wrong structure - then should return body with null data`() {
        //Given
        val expected = WsUsers(
            page = 0,
            data = null
        )
        mockWebServer.enqueueWithCode("wrong_users.json", 200)
        runBlocking {
            // When
            val apiResponse = api.getUsers()

            //Then
            assertThat(apiResponse.isSuccessful).isTrue
            assertThat(apiResponse.body()).isEqualTo(expected)
        }
    }

}