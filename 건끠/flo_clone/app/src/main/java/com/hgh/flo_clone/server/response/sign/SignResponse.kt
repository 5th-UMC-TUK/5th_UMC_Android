package com.hgh.flo_clone.server.response.sign

data class SignResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String
)