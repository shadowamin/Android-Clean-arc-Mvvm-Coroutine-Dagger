package com.hannibalprojects.sampleproject.data.remote

import com.squareup.moshi.Json

data class WsUsers(val page: Int, val data: List<WsUser>?)

data class WsUser(
    val id: Int,
    val email: String?,
    @field:Json(name = "first_name") val firstName: String?,
    @field:Json(name = "last_name") val lastName: String?,
    val avatar: String?
)