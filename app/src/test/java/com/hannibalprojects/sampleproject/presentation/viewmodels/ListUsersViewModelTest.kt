package com.hannibalprojects.sampleproject.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import com.hannibalprojects.sampleproject.TestCoroutineRule
import com.hannibalprojects.sampleproject.domain.User
import com.hannibalprojects.sampleproject.domain.usecases.GetUsersUseCase
import com.hannibalprojects.sampleproject.domain.usecases.RefreshUsersUseCase
import com.hannibalprojects.sampleproject.domain.usecases.UseCase
import kotlinx.coroutines.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class ListUsersViewModelTest {
    @Mock
    lateinit var usersUseCase: GetUsersUseCase
    @Mock
    lateinit var refreshUsersUseCase: RefreshUsersUseCase

    @Mock
    lateinit var mockedDispacher : MainCoroutineDispatcher



    lateinit var listUsersViewModel: ListUsersViewModel

/*    @get:Rule
    val testCoroutineRule =
        TestCoroutineRule()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()*/

    @Before
    fun prepareMocks() {
        listUsersViewModel = ListUsersViewModel(usersUseCase, refreshUsersUseCase)
    }

    @Test
    fun `test if getUsersUseCase is invoked`() {
       /* val response: UseCase.Response<DataSource.Factory<Int, User>>.() -> Unit = {
        }
       val result = Job()
        Mockito.`when`(CoroutineScope(mockedDispacher).launch {}).thenReturn(result)
        Mockito.`when`(usersUseCase.execute(response)).thenReturn(Unit)
        //   Mockito.doNothing().`when`(usersUseCase).execute(response)
        listUsersViewModel.loadUsers(response)
        Mockito.verify(usersUseCase).execute(response)*/

    }

    @Test
    fun `test if refreshUsersUseCase is invoked`() {


    }
}