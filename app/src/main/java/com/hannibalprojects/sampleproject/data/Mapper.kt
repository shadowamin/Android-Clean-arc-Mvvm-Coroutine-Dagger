package com.hannibalprojects.sampleproject.data

import com.hannibalprojects.sampleproject.data.remote.WsUser
import com.hannibalprojects.sampleproject.domain.User

class Mapper {
    companion object{
        internal fun WsUser.toDomainUser() = User(
            id = id,
            email = email,
            firstName = firstName,
            lastName = lastName,
            avatar = avatar
        )
    }
}