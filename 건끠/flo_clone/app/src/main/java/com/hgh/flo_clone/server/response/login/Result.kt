package com.hgh.flo_clone.server.response.login

data class Result(
    val jwt: String,
    val userIdx: Int
)