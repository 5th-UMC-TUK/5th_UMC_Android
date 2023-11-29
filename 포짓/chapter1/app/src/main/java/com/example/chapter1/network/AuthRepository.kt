package com.example.chapter1.network

import android.util.Log
import com.example.chapter1.BuildConfig
import com.example.chapter1.model.AuthResponse
import com.example.chapter1.model.Login
import com.example.chapter1.model.LoginResponse
import com.example.chapter1.model.User
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AuthRepository {
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(18, TimeUnit.SECONDS)
        .writeTimeout(18, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    private val retrofit = Retrofit.Builder().baseUrl(BuildConfig.ipApi)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var api = retrofit.create(AuthRetrofitInterface::class.java)

    fun registUser(user: User): AuthResponse? {
        val singUp = api.signUp(user)
        return try {
            val result = singUp.execute()
            Log.d("result", "회원가입 결과: ${result.code()}")
            result.body()!!
        } catch (e: Exception) {
            Log.d("error", "회원가입 error: ${e.message}")
            null
        }
    }

    fun loginUser(login: Login): LoginResponse? {
        val login = api.loginUser(login)
        return try {
            val result = login.execute()
            Log.d("result", "로그인 결과: ${result.code()}")
            result.body()!!
        } catch (e: Exception) {
            Log.d("error", "로그인 error: ${e.message}")
            null
        }
    }

    fun autoLoginUser(token: String): AuthResponse? {
        val auto = api.autoLogin(token)
        return try {
            val result = auto.execute()
            Log.d("result", "자동로그인 결과: ${result.code()}")
            result.body()!!
        } catch (e: Exception) {
            Log.d("error", "자동로그인 error: ${e.message}")
            null
        }
    }

}