package com.pereyrarg11.navigation.auth.data.di

import com.pereyrarg11.navigation.auth.data.AuthRepositoryImpl
import com.pereyrarg11.navigation.auth.data.register.EmailPatternValidator
import com.pereyrarg11.navigation.auth.data.register.NamePatternValidator
import com.pereyrarg11.navigation.auth.domain.AuthRepository
import com.pereyrarg11.navigation.auth.domain.register.PatternValidator
import com.pereyrarg11.navigation.auth.domain.register.ValidateEmailUseCase
import com.pereyrarg11.navigation.auth.domain.register.ValidateLastNameUseCase
import com.pereyrarg11.navigation.auth.domain.register.ValidateNameUseCase
import com.pereyrarg11.navigation.auth.domain.register.ValidatePhoneNumberUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module {
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
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
}
