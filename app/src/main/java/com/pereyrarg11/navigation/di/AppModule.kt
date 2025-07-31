package com.pereyrarg11.navigation.di

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.pereyrarg11.navigation.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    // this dependency is an Android specific stuff
    single<SharedPreferences> {
        EncryptedSharedPreferences(
            androidApplication(),
            "app_pref",
            MasterKey(androidApplication()),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM

        )
    }
    viewModelOf(::MainViewModel)
}
