package com.pereyrarg11.navigation.core.domain.session

interface SessionStorage {
    suspend fun get(): AuthInfo?
    suspend fun set(info: AuthInfo?)
}
