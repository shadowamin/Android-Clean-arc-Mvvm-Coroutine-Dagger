package com.hannibalprojects.sampleproject.presentation.viewmodels

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.Observer
import com.hannibalprojects.sampleproject.domain.User
import com.hannibalprojects.sampleproject.domain.usecases.GetUserUseCase
import com.hannibalprojects.sampleproject.presentation.models.DataWrapper
import com.hannibalprojects.sampleproject.presentation.models.Failure
import com.hannibalprojects.sampleproject.presentation.models.RequestError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
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

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(MockitoExtension::class)
class UserDetailsViewModelTest {

    @Mock
    lateinit var useCase: GetUserUseCase

    @InjectMocks
    lateinit var viewModel: UserDetailsViewModel

    @Mock
    lateinit var loadUserObserver: Observer<DataWrapper<User>>

    private val dispatcher = TestCoroutineDispatcher()

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

    private val user = User(
        id = 3,
        email = "user@email.com",
        firstName = "firstName",
        lastName = "lastName",
        avatar = "avatar"
    )

    @Test
    fun `getUserDetails - when response - then pass value to observer`() = dispatcher.runBlockingTest {
        // Given
        given(useCase.execute(3)).willReturn(user)

        // When
        viewModel.getUserDetails(3)

        // Then
        then(useCase).should().execute(3)
        assertThat(viewModel.observableUser.get()).isEqualTo(user)
        assertThat(viewModel.loadUsersLiveData.value).isNull()
    }

    @Test
    fun `getUserDetails - when Exception - then post ErrorRequest`() = dispatcher.runBlockingTest {
        // Given
        val exception = RuntimeException()
        given(useCase.execute(3)).willThrow(exception)
        viewModel.loadUsersLiveData.observeForever(loadUserObserver)

        // When
        viewModel.getUserDetails(3)

        // Then
        then(useCase).should().execute(3)
        verify(loadUserObserver).onChanged(Failure(RequestError(exception)))
        assertThat(viewModel.observableUser.get()).isNull()
        assertThat(viewModel.loadUsersLiveData.value is Failure)
    }
}