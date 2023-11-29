package com.example.chapter1.model

data class AlbumIncludingSongResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: List<ResultXXX>
)