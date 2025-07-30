package com.pereyrarg11.navigation.auth.data.register

import com.pereyrarg11.navigation.auth.domain.register.User

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
