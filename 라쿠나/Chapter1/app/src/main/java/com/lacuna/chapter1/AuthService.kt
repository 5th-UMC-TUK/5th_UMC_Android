package com.lacuna.chapter1

import android.util.Log
import com.lacuna.chapter1.data.AuthResponse
import com.lacuna.chapter1.data.User
import retrofit2.Call
import retrofit2.Response

class AuthService {
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView
    private lateinit var autoLoginView: AutoLoginView
    fun setSignUpView(signUpView: SignUpView){
        this.signUpView = signUpView
    }
    fun setLoginView(loginView: LoginView){
        this.loginView = loginView
    }
    fun setAutoLoginView(autoLoginView: AutoLoginView){
        this.autoLoginView = autoLoginView
    }

    fun signUp(user: User) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.signUp(user).enqueue(object : retrofit2.Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("SIGNUP/SUCCESS", response.toString())
                val resp : AuthResponse = response.body()!!
                when(resp.code) {
                    1000 -> signUpView.onSignUpSuccess()
                    else -> signUpView.onSignUpFailure(resp.message)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("SIGNUP/FAILURE", t.message.toString())
            }
        })
        Log.d("SIGNUP", "HELLO")

    }

    fun login(user: User) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.login(user).enqueue(object : retrofit2.Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("LOGIN/SUCCESS", response.toString())
                val resp : AuthResponse = response.body()!!
                when(val code = resp.code) {
                    1000 -> loginView.onLoginSuccess(code, resp.result!!)
                    else -> loginView.onLoginFailure(resp.message)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("LOGIN/FAILURE", t.message.toString())
            }

        })
        Log.d("LOGIN", "HELLO")
    }
    fun autoLogin(jwt: String) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.autoLogin(jwt).enqueue(object : retrofit2.Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("AUTOLOGIN/SUCCESS", response.toString())
                val resp : AuthResponse = response.body()!!
                when(val code = resp.code) {
                    1000 -> autoLoginView.onAutoLoginSuccess()
                    else -> autoLoginView.onAutoLoginFailure()
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("AUTOLOGIN/FAILURE", t.message.toString())
            }

        })
        Log.d("AUTOLOGIN", "HELLO")
    }
}