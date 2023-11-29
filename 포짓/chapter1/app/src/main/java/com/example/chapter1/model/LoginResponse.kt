package com.example.chapter1.model

data class LoginResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)