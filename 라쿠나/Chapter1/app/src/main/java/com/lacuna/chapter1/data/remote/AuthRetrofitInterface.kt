package com.lacuna.chapter1.data.remote

import com.lacuna.chapter1.data.AuthResponse
import com.lacuna.chapter1.data.entity.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/users")
    fun signUp(@Body user : User) : Call<AuthResponse>

    @POST("/users/login")
    fun login(@Body user : User) : Call<AuthResponse>

    @GET("/users/auto-login")
    fun autoLogin(@Header("X-ACCESS-TOKEN") jwt : String ) : Call<AuthResponse>
}