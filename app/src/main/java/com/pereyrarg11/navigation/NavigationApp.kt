package com.pereyrarg11.navigation

import android.app.Application
import com.pereyrarg11.navigation.account.data.di.accountDataModule
import com.pereyrarg11.navigation.account.presentation.di.accountPresentationModule
import com.pereyrarg11.navigation.core.data.SecretKeys
import com.pereyrarg11.navigation.core.data.di.coreDataModule
import com.pereyrarg11.navigation.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NavigationApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NavigationApp)
            androidLogger()
            modules(
                appModule,
                coreDataModule,
                accountDataModule,
                accountPresentationModule,
            )
            properties(
                mapOf(
                    SecretKeys.AUTH_APP_TYPE.name to BuildConfig.AUTH_APP_TYPE,
                    SecretKeys.AUTH_DEVICE_TYPE.name to BuildConfig.AUTH_DEVICE_TYPE,
                    SecretKeys.AUTH_URL.name to BuildConfig.AUTH_URL,
                )
            )
        }
    }
}
