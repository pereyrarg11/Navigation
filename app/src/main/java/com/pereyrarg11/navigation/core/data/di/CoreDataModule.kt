package com.pereyrarg11.navigation.core.data.di

import com.pereyrarg11.navigation.core.data.SecretKeys
import com.pereyrarg11.navigation.core.data.SecretValues
import com.pereyrarg11.navigation.core.data.networking.HttpClientFactory
import com.pereyrarg11.navigation.core.data.networking.SessionHttpClientFactory
import com.pereyrarg11.navigation.core.data.session.SessionStorageImpl
import com.pereyrarg11.navigation.core.domain.session.SessionStorage
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single {
        SecretValues(
            authAppType = getProperty(SecretKeys.AUTH_APP_TYPE.name),
            authDeviceType = getProperty(SecretKeys.AUTH_DEVICE_TYPE.name),
            authUrl = getProperty(SecretKeys.AUTH_URL.name),
        )
    }
    single<HttpClient>(named("AuthNoneClient")) {
        HttpClientFactory().build()
    }
    single<HttpClient>(named("AuthBearerClient")) {
        SessionHttpClientFactory(
            sessionStorage = get(),
            secretValues = get()
        ).build()
    }
    singleOf(::SessionStorageImpl).bind<SessionStorage>()
}
