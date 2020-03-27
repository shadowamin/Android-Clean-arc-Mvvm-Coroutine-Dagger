package com.hannibalprojects.sampleproject.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.hannibalprojects.sampleproject.TestCoroutineRule
import com.hannibalprojects.sampleproject.data.local.LocalDataSource
import com.hannibalprojects.sampleproject.data.remote.RemoteDataSource
import com.hannibalprojects.sampleproject.domain.User
import com.hannibalprojects.sampleproject.domain.UsersResponse
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class UsersDataSourceImplTest {

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    private lateinit var localDataSource: LocalDataSource

    private lateinit var dataSource: UsersDataSource

    @get:Rule
    val testCoroutineRule =
        TestCoroutineRule()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun prepareMocks() {
        dataSource = UsersDataSourceImpl(localDataSource, remoteDataSource)
    }

    @Test
    fun `Test If getUsers get list of users`() {
        testCoroutineRule.runBlockingTest {
            dataSource.getUsers()
            Mockito.verify(localDataSource).getUsers()
        }
    }

    @Test
    fun `Test If getUser get user`() {
        testCoroutineRule.runBlockingTest {
            val user = User(3, "mail", "john", "smith", "")
            val liveUser = MutableLiveData<User>()
            liveUser.value = user
            Mockito.`when`(localDataSource.getUser(2)).thenReturn(liveUser)
            val result = dataSource.getUser(2)
            Mockito.verify(localDataSource).getUser(anyInt())
            assertEquals(result.value?.firstName, "john")
        }
    }


    @Test
    fun `Test If refresh Users invok the right fun`() {
        testCoroutineRule.runBlockingTest {
            val list = arrayListOf(User(3, "mail", "john", "smith", ""))
            Mockito.`when`(remoteDataSource.getUsers()).thenReturn(list)
            val response = dataSource.refreshUsers()
            Mockito.verify(remoteDataSource).getUsers()
            Mockito.verify(localDataSource).insertUsers(anyList())
            assertEquals(response.code, 200)
        }
    }


}