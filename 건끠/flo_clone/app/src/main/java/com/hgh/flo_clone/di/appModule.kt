package com.hgh.flo_clone.di

import com.hgh.flo_clone.db.preference.ApplicationPreferenceManager
import com.hgh.flo_clone.server.repository.ApiRepository
import com.hgh.flo_clone.server.repository.DefaultApiRepository
import com.hgh.flo_clone.server.repository.DefaultUserRepository
import com.hgh.flo_clone.server.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {

    single { Dispatchers.IO }
    single { Dispatchers.Main }

    single { provideRetrofit(get(), get()) }
    single { provideGsonConvertFactory() }
    single { buildOkHttpClient() }

    single { provideServerApi(get()) }

    single<ApiRepository> { DefaultApiRepository(get(), get()) }
    single { ApplicationPreferenceManager(androidApplication()) }
    single<UserRepository> { DefaultUserRepository(get(), get()) }
}