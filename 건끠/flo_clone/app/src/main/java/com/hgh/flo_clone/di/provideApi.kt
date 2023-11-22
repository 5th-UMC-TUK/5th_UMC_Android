package com.hgh.flo_clone.di

import com.hgh.flo_clone.server.network.ApiService
import com.hgh.flo_clone.server.network.Url
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


fun provideServerApi(retrofit: Retrofit): ApiService{
    return retrofit.create(ApiService::class.java)
}

fun provideRetrofit(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Url.Flo_SERVER)
        .addConverterFactory(gsonConverterFactory)
        .client(okHttpClient)
        .build()
}

fun provideGsonConvertFactory(): GsonConverterFactory {
    return GsonConverterFactory.create()
}

fun buildOkHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.NONE
    }
//    if (BuildConfig.DEBUG) {
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//    } else {
//        interceptor.level = HttpLoggingInterceptor.Level.NONE
//    }
    return OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()
}