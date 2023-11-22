package com.hgh.flo_clone.server.response.login

data class loginResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)