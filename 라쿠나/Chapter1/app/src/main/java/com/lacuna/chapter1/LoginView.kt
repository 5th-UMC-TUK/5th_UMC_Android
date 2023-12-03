package com.lacuna.chapter1
import com.lacuna.chapter1.data.Result

interface LoginView {
    fun onLoginSuccess(code: Int, result: Result)
    fun onLoginFailure(message : String)
}