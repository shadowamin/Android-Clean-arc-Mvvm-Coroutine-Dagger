package com.hannibalprojects.sampleproject.data.remote

import com.google.gson.annotations.SerializedName

data class WsUsers(val page: Int, val data: List<WsUser>?)

data class WsUser(
    val id: Int,
    val email: String?,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("last_name") val lastName: String?,
    val avatar: String?
)