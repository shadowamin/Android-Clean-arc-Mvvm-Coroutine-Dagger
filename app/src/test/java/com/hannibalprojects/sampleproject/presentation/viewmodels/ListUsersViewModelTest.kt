package com.hannibalprojects.sampleproject.presentation.viewmodels

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.Observer
import com.hannibalprojects.sampleproject.domain.User
import com.hannibalprojects.sampleproject.domain.usecases.GetUsersUseCase
import com.hannibalprojects.sampleproject.presentation.models.DataWrapper
import com.hannibalprojects.sampleproject.presentation.models.Failure
import com.hannibalprojects.sampleproject.presentation.models.RequestError
import com.hannibalprojects.sampleproject.presentation.models.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension

@ExperimentalCoroutinesApi
@ExtendWith(MockitoExtension::class)
class ListUsersViewModelTest {

    @Mock
    lateinit var usersUseCase: GetUsersUseCase

    @InjectMocks
    lateinit var listUsersViewModel: ListUsersViewModel

    @Mock
    lateinit var refreshObserver: Observer<DataWrapper<Boolean>>

    @Mock
    lateinit var listUsersObserver: Observer<DataWrapper<List<User>>>

    private val dispatcher = TestCoroutineDispatcher()

    private val user = User(
        id = 3,
        email = "user@email.com",
        firstName = "firstName",
        lastName = "lastName",
        avatar = "avatar"
    )

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        ArchTaskExecutor.getInstance()
            .setDelegate(object : TaskExecutor() {
                override fun executeOnDiskIO(runnable: Runnable) = runnable.run()

                override fun postToMainThread(runnable: Runnable) = runnable.run()

                override fun isMainThread(): Boolean = true
            })
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        ArchTaskExecutor.getInstance().setDelegate(null)
    }

    @Test
    fun `loadUsers - when response - then post value to observer`() = dispatcher.runBlockingTest {
        // Given
            val listUsers = listOf(user)

        given(usersUseCase.execute()).willReturn(listUsers)
        listUsersViewModel.loadUsersLiveData.observeForever(listUsersObserver)

        // When
        listUsersViewModel.loadUsers()

        // Then
        then(usersUseCase).should().execute()
        verify(listUsersObserver).onChanged(Success(listUsers))
        listUsersViewModel.loadUsersLiveData.removeObserver(listUsersObserver)
    }

    @Test
    fun `loadUsers - when error - then post Failure`() = dispatcher.runBlockingTest {
        // Given
        val exception = RuntimeException()
        given(usersUseCase.execute()).willThrow(exception)
        listUsersViewModel.loadUsersLiveData.observeForever(listUsersObserver)

        // When
        listUsersViewModel.loadUsers()

        // Then
        then(usersUseCase).should().execute()
        verify(listUsersObserver).onChanged(Failure(RequestError(exception)))
        listUsersViewModel.loadUsersLiveData.removeObserver(listUsersObserver)
    }

}