package com.pereyrarg11.navigation.core.data.di

import com.pereyrarg11.navigation.core.data.networking.HttpClientFactory
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory().build()
    }
}
