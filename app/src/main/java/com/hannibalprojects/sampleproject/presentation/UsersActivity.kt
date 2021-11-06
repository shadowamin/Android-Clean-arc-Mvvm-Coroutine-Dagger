package com.hannibalprojects.sampleproject.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hannibalprojects.sampleproject.presentation.uicomponent.LoadUserProfile
import com.hannibalprojects.sampleproject.presentation.uicomponent.UsersListComponent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersActivity : AppCompatActivity() {

    private val viewModel: UsersViewModel by viewModels()

    companion object {
        private const val USERS_LIST_SCREEN = "USERS_LIST"
        private const val USER_ID = "{userId}"
        private const val USER_DETAILS_SCREEN = "USER_DETAILS/$USER_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavHostController()
        }
        viewModel.loadUsers()
        viewModel.refreshUsers(false)
    }

    @Composable
    private fun NavHostController() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = USERS_LIST_SCREEN) {
            composable(USERS_LIST_SCREEN) {
                UsersListComponent(
                    viewModel = viewModel,
                ) {
                    navController.navigate(
                        USER_DETAILS_SCREEN.replace(USER_ID, it.id.toString())
                    )
                }
            }
            composable(USER_DETAILS_SCREEN) { backStackEntry ->
                LoadUserProfile(
                    userId = backStackEntry.arguments?.getString("userId"),
                    viewModel = viewModel
                )
            }
        }
    }

}