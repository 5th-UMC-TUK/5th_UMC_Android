package com.hgh.flo_clone.server.response.song

data class SongResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)