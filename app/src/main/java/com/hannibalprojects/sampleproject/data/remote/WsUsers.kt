package com.hannibalprojects.sampleproject.data.remote

import com.hannibalprojects.sampleproject.domain.User

data class WsUsers(val page: Int, val data: List<User>)