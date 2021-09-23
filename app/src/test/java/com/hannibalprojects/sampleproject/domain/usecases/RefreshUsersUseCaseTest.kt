package com.hannibalprojects.sampleproject.domain.usecases

import com.hannibalprojects.sampleproject.domain.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension


@ExperimentalCoroutinesApi
@ExtendWith(MockitoExtension::class)
class RefreshUsersUseCaseTest {

    @Mock
    lateinit var repository: Repository

    @InjectMocks
    lateinit var refreshUsersUseCase: RefreshUsersUseCase

    @Test
    fun executeTask(): Unit = runBlocking {
        given(repository.refreshUsers()).willReturn(true)
        val result = refreshUsersUseCase.execute()
        assertThat(result).isEqualTo(true)
        then(repository).should().refreshUsers()
    }
}