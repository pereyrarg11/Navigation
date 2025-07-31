package com.pereyrarg11.navigation.account.presentation.di

import com.pereyrarg11.navigation.account.presentation.login.LoginViewModel
import com.pereyrarg11.navigation.account.presentation.profile.ProfileViewModel
import com.pereyrarg11.navigation.account.presentation.signin.SigninViewModel
import com.pereyrarg11.navigation.account.presentation.verification.VerificationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val accountPresentationModule = module {
    viewModelOf(::SigninViewModel)
    viewModel { parameters ->
        VerificationViewModel(
            phoneNumber = parameters.get(),
            validateCode = get(),
            repository = get(),
        )
    }
    viewModelOf(::LoginViewModel)
    viewModelOf(::ProfileViewModel)
}
