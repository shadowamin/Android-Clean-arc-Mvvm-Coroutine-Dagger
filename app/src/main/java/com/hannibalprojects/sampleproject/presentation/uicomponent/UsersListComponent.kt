package com.hannibalprojects.sampleproject.presentation.uicomponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hannibalprojects.sampleproject.domain.User
import com.hannibalprojects.sampleproject.presentation.UsersViewModel
import com.hannibalprojects.sampleproject.presentation.models.Failure
import com.hannibalprojects.sampleproject.presentation.models.Loading
import com.hannibalprojects.sampleproject.presentation.models.Success
import com.hannibalprojects.sampleproject.presentation.utils.ErrorManager

@Composable
fun UsersListComponent(viewModel: UsersViewModel, navigateToDetails: (User) -> Unit) {
    val liveDataState by viewModel.loadUsersLiveData.observeAsState(initial = Loading(true))
    val refreshState by viewModel.refreshUsersLiveData.observeAsState(false)

    SwipeRefresh(
        state = rememberSwipeRefreshState(refreshState),
        onRefresh = { viewModel.refreshUsers(true) },
    ) {
        when (liveDataState) {
            is Success -> {
                val users = (liveDataState as Success<List<User>>).data
                UsersList(
                    users = users,
                    navigateToDetails = navigateToDetails
                )
            }
            is Failure -> {
                val error = (liveDataState as Failure).error
                ErrorManager.displayError(LocalContext.current, error)
            }
            is Loading -> {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun UsersList(users: List<User>, navigateToDetails: (User) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(
            horizontal =
            8.dp, vertical = 8.dp
        )
    ) {
        items(
            items = users,
            itemContent = {
                UserItem(user = it, navigateToDetails)
            }
        )
    }
}

@Composable
private fun UserItem(user: User, navigateToDetails: (User) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(2.dp))
    ) {
        Row(Modifier.clickable { navigateToDetails(user) }) {
            Image(
                painter = rememberImagePainter(user.avatar),
                contentDescription = null,
                modifier = Modifier.size(128.dp)
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = "${user.firstName} ${user.lastName}", style = MaterialTheme.typography.h5)
            }
        }
    }
}