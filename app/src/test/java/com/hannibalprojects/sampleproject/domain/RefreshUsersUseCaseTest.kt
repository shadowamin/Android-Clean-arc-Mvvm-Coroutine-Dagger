package com.hannibalprojects.sampleproject.domain

import com.hannibalprojects.sampleproject.TestCoroutineRule
import com.hannibalprojects.sampleproject.domain.usecases.RefreshUsersUseCase
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RefreshUsersUseCaseTest {

    @Mock
    lateinit var repository: Repository

    lateinit var refreshUsersUseCase: RefreshUsersUseCase

    @get:Rule
    val testCoroutineRule =
        TestCoroutineRule()

    @Before
    fun prepareMock() {
        refreshUsersUseCase = RefreshUsersUseCase(repository)
    }

    @Test
    fun `Test IF refresh get the expected response`() {
      /*  testCoroutineRule.runBlockingTest {
            Mockito.`when`(repository.refreshUsers()).thenReturn(UsersResponse(200, "message"))
            refreshUsersUseCase.execute {
                onResponse {
                    Assert.assertEquals(it.code, 200)
                }
            }

        }*/
    }

}