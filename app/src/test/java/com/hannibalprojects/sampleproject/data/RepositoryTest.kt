package com.hannibalprojects.sampleproject.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.hannibalprojects.sampleproject.TestCoroutineRule
import com.hannibalprojects.sampleproject.domain.Repository
import com.hannibalprojects.sampleproject.domain.User
import com.hannibalprojects.sampleproject.domain.UsersResponse
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {

    @Mock
    lateinit var usersDataSource: UsersDataSource
    @get:Rule
    val testCoroutineRule =
        TestCoroutineRule()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    lateinit var repository: Repository

    @Before
    fun prepareMock() {
        repository = RepositoryImpl(usersDataSource)
    }

    @Test
    fun `Test If refreshUsers get the right response`() {
        testCoroutineRule.runBlockingTest {
            Mockito.`when`(usersDataSource.refreshUsers()).thenReturn(UsersResponse(200, "message"))
            Assert.assertEquals(repository.refreshUsers().code, 200)
            Mockito.verify(usersDataSource).refreshUsers()
            Mockito.`when`(usersDataSource.refreshUsers()).thenReturn(UsersResponse(505, "error"))
            Assert.assertEquals(repository.refreshUsers().code, 505)
        }
    }

    @Test
    fun `Test If getUser get user`() {
        val user = User(3, "mail", "john", "smith", "")
        val liveUser = MutableLiveData<User>()
        liveUser.value = user

        testCoroutineRule.runBlockingTest {
            Mockito.`when`(usersDataSource.getUser(2)).thenReturn(liveUser)
            repository.getUser(2).observeForever {
                Assert.assertEquals(it.firstName, "john")
            }
        }

    }

    @Test
    fun `Test If getUsers get the List mocked in dataSource`() {
        testCoroutineRule.runBlockingTest {
            repository.getUsers()
            Mockito.verify(usersDataSource).refreshUsers()
        }
    }

}