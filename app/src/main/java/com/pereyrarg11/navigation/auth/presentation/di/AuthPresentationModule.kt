package com.pereyrarg11.navigation.auth.presentation.di

import com.pereyrarg11.navigation.auth.presentation.register.RegisterViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule = module {
    viewModelOf(::RegisterViewModel)
}
