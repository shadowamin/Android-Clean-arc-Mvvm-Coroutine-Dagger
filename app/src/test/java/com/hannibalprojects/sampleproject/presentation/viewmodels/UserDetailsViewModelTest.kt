package com.hannibalprojects.sampleproject.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import com.hannibalprojects.sampleproject.TestCoroutineRule
import com.hannibalprojects.sampleproject.domain.User
import com.hannibalprojects.sampleproject.domain.usecases.GetUserUseCase
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserDetailsViewModelTest {

    @Mock
    lateinit var userUseCase: GetUserUseCase

    lateinit var userDetailsViewModel: UserDetailsViewModel

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun prepareMocks() {
        userDetailsViewModel = UserDetailsViewModel(userUseCase)
    }

    fun `test if getUserUseCase bring the correct user`() {

    }


}