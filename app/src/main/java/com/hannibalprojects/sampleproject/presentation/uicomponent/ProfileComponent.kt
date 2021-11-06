package com.hannibalprojects.sampleproject.presentation.uicomponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.hannibalprojects.sampleproject.domain.User
import com.hannibalprojects.sampleproject.presentation.UsersViewModel
import com.hannibalprojects.sampleproject.presentation.models.Failure
import com.hannibalprojects.sampleproject.presentation.models.Loading
import com.hannibalprojects.sampleproject.presentation.models.Success
import com.hannibalprojects.sampleproject.presentation.utils.ErrorManager

@Preview
@Composable
fun PreviewUserProfile() {
    DisplayUserProfile(
        User(
            id = 1,
            firstName = "User First Name",
            lastName = "User Last Name",
            email = "User@email.com",
            avatar = ""
        )
    )
}

@Composable
fun LoadUserProfile(userId: String?, viewModel: UsersViewModel) {
    userId?.let {
        viewModel.getUserDetails(it.toInt())
        val liveDataState by viewModel.loadUserLiveData.observeAsState(initial = Loading(true))
        when (liveDataState) {
            is Success -> {
                val user = (liveDataState as Success<User>).data
                DisplayUserProfile(user)
            }
            is Failure -> {
                val error = (liveDataState as Failure).error
                ErrorManager.displayError(LocalContext.current, error)
            }
            is Loading -> CircularProgressIndicator()
        }
    }
}

@Composable
private fun DisplayUserProfile(user: User) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = rememberImagePainter(user.avatar),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(text = user.firstName ?: "", style = MaterialTheme.typography.h5)
        Text(text = user.lastName ?: "", style = MaterialTheme.typography.h5)
        Text(text = user.email ?: "", style = MaterialTheme.typography.h5)
    }
}