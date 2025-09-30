package com.pereyrarg11.navigation.account.data.signin

import com.pereyrarg11.navigation.account.domain.signin.User

fun UserDto.toUser(): User {
    return User(
        name = name.orEmpty(),
        lastName = lastName.orEmpty(),
        email = email.orEmpty(),
        phone = phone.orEmpty(),
        avatarUrl = mediaPath.orEmpty(),
        uuid = uuid.orEmpty(),
    )
}
