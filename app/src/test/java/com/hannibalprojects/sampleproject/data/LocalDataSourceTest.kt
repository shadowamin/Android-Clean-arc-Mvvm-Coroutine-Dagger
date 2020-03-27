package com.hannibalprojects.sampleproject.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.hannibalprojects.sampleproject.TestCoroutineRule
import com.hannibalprojects.sampleproject.data.local.LocalDataSource
import com.hannibalprojects.sampleproject.data.local.LocalDataSourceImpl
import com.hannibalprojects.sampleproject.data.local.UserDao
import com.hannibalprojects.sampleproject.domain.User
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class LocalDataSourceTest() {


    @Mock
    lateinit var userDao: UserDao


    lateinit var localDataSource: LocalDataSource

    @get:Rule
    val testCoroutineRule =
        TestCoroutineRule()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun prepareMocks() {
        localDataSource = LocalDataSourceImpl(userDao)
    }

    @Test
    fun `Test If getUser get the user needed`() {
        testCoroutineRule.runBlockingTest {
            val user = User(3, "mail", "john", "smith", "")
            val liveUser = MutableLiveData<User>()
            liveUser.value = user
            Mockito.`when`(userDao.getUser(2)).thenReturn(liveUser)
            Assert.assertEquals(localDataSource.getUser(2).value?.firstName, "john")
            Mockito.verify(userDao).getUser(anyInt())
        }
    }

    @Test
    fun `Test if getUsers is Invoked`(){
        testCoroutineRule.runBlockingTest {
            localDataSource.getUsers()
            Mockito.verify(userDao).getAllLiveUsers()
        }
    }

    @Test
    fun `Test if insert User is called`() {
        val user = User(3, "mail", "john", "smith", "")
        val listUsers = arrayListOf(user)
        Mockito.`when`(userDao.insertAllUsers(listUsers)).thenReturn(arrayListOf(10L, 12L))
        testCoroutineRule.runBlockingTest {
            localDataSource.insertUsers(listUsers)
            Mockito.verify(userDao).insertAllUsers(anyList())
        }
    }

}