package com.hannibalprojects.sampleproject.data

import com.hannibalprojects.sampleproject.data.local.UserEntity
import com.hannibalprojects.sampleproject.data.remote.WsUser
import com.hannibalprojects.sampleproject.domain.User

class Mapper {
    companion object{
        internal fun UserEntity.toDomainUser() = User(
            id = id,
            email = email,
            firstName = firstName,
            lastName = lastName,
            avatar = avatar
        )

        internal fun WsUser.toEntity() = UserEntity(
            id = id,
            email = email,
            firstName = firstName,
            lastName = lastName,
            avatar = avatar
        )
    }
}