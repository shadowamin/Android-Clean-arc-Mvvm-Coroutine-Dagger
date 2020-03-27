package com.hannibalprojects.sampleproject.data

import com.hannibalprojects.sampleproject.TestCoroutineRule
import com.hannibalprojects.sampleproject.data.remote.RemoteDataSource
import com.hannibalprojects.sampleproject.data.remote.RemoteDataSourceImpl
import com.hannibalprojects.sampleproject.data.remote.UserApi
import com.hannibalprojects.sampleproject.data.remote.WsUsers
import com.hannibalprojects.sampleproject.domain.User
import junit.framework.Assert.assertEquals
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RemoteDataSourceTest {

    @Mock
    private lateinit var userApi: UserApi

    @get:Rule
    val testCoroutineRule =
        TestCoroutineRule()

    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun prepareMocks() {
        remoteDataSource = RemoteDataSourceImpl(userApi)
    }


    @Test
    fun `Test if we get the right response and right result from ws`() {
        testCoroutineRule.runBlockingTest {
            val listUsers = arrayListOf(
                User(1, "m", "jhon", "doe", ""),
                User(2, "mail", "jane", "smith", "")
            )
            Mockito.`when`(userApi.getUsers()).thenReturn(getMockedSuccessResponseWs(listUsers))
            val wsUsers = remoteDataSource.getUsers()
            Mockito.verify(userApi).getUsers()
            assertEquals(wsUsers?.size, 2)
            assertEquals(wsUsers?.get(1)?.firstName, "jane")

            Mockito.`when`(userApi.getUsers()).thenReturn(getMockedErrorResponseWs())
            assertEquals(remoteDataSource.getUsers(), null)
        }


    }

    private fun getMockedSuccessResponseWs(usersList: List<User>): retrofit2.Response<WsUsers> {
        return retrofit2.Response.success(200, WsUsers(1, usersList))
    }

    private fun getMockedErrorResponseWs(): retrofit2.Response<WsUsers> {
        return retrofit2.Response.error(
            404,
            ResponseBody.create(MediaType.parse("application/json"), "".toByteArray())
        )
    }
}