package com.pereyrarg11.navigation

import android.app.Application
import com.pereyrarg11.navigation.auth.data.di.authDataModule
import com.pereyrarg11.navigation.auth.presentation.di.authPresentationModule
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
                authDataModule,
                authPresentationModule,
            )
        }
    }
}
