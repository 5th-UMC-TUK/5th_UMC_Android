package com.lacuna.chapter1

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://edu-api-test.softsquared.com" // /를 붙이게 되면 AuthRetrofitInterface에서 @POST("/users") 이 부분의 /는 빼야함

fun getRetrofit(): Retrofit { // retrofit 객체 생성
    val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    return retrofit
}