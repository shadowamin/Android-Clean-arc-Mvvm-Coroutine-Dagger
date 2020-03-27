package com.hannibalprojects.sampleproject.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.hannibalprojects.sampleproject.TestCoroutineRule
import com.hannibalprojects.sampleproject.domain.usecases.GetUserUseCase
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
class GetUserUseCaseTest {

    @Mock
    lateinit var repository: Repository

    lateinit var getUserUseCase: GetUserUseCase
    @get:Rule
    val testCoroutineRule =
        TestCoroutineRule()


    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun prepareMocks() {
        getUserUseCase = GetUserUseCase(repository)
    }

    @Test
    fun `Test if the execute gives the right user`() {

      /*  testCoroutineRule.runBlockingTest {
            val user = User(3, "mail", "john", "smith", "")
            val liveUser = MutableLiveData<User>()
            liveUser.value = user
            Mockito.`when`(repository.getUser(2)).thenReturn(liveUser)
            getUserUseCase.userId = 2
            getUserUseCase.execute {
                onComplet {
                    Assert.assertEquals(it,liveUser)
                }
                onResponse { }
            }
            Mockito.verify(repository).getUser(2)
        }*/


    }

}