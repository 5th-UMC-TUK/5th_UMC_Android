package com.example.chapter1.network

import com.example.chapter1.model.AuthResponse
import com.example.chapter1.model.Login
import com.example.chapter1.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("users")
    fun signUp(@Body user: com.example.chapter1.model.User): Call<AuthResponse>

    @POST("users/login")
    fun loginUser(@Body user: Login): Call<LoginResponse>

    @GET("users/auto-login")
    fun autoLogin(@Header("X-ACCESS-TOKEN") token: String): Call<AuthResponse>
}