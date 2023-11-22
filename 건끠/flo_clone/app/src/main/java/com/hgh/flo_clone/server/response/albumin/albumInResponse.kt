package com.hgh.flo_clone.server.response.albumin

data class albumInResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: List<Result>
)