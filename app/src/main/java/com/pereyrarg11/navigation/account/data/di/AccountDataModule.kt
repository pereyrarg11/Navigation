package com.pereyrarg11.navigation.account.data.di

import com.pereyrarg11.navigation.account.data.AuthRepositoryImpl
import com.pereyrarg11.navigation.account.data.signin.EmailPatternValidator
import com.pereyrarg11.navigation.account.data.signin.NamePatternValidator
import com.pereyrarg11.navigation.account.domain.AuthRepository
import com.pereyrarg11.navigation.account.domain.signin.PatternValidator
import com.pereyrarg11.navigation.account.domain.signin.ValidateEmailUseCase
import com.pereyrarg11.navigation.account.domain.signin.ValidateLastNameUseCase
import com.pereyrarg11.navigation.account.domain.signin.ValidateNameUseCase
import com.pereyrarg11.navigation.account.domain.signin.ValidatePhoneNumberUseCase
import com.pereyrarg11.navigation.account.domain.verification.ValidateOtpUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val accountDataModule = module {
    // signup
    single<PatternValidator>(named("EmailPattern")) {
        EmailPatternValidator
    }
    single<PatternValidator>(named("ProperNamePattern")) {
        NamePatternValidator
    }
    single {
        ValidateEmailUseCase(get(named("EmailPattern")))
    }
    single {
        ValidateNameUseCase(get(named("ProperNamePattern")))
    }
    single {
        ValidateLastNameUseCase(get(named("ProperNamePattern")))
    }
    singleOf(::ValidatePhoneNumberUseCase)
    single<AuthRepository> {
        AuthRepositoryImpl(
            httpClient = get(named("AuthNoneClient")),
            userHttpClient = get(named("AuthBearerClient")),
            secrets = get(),
            sessionStorage = get(),
        )
    }
    // verify
    singleOf(::ValidateOtpUseCase)
}
