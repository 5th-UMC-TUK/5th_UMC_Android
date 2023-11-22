package com.hgh.flo_clone.server.response.album

data class AlbumResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)