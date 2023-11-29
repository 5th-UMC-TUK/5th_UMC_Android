package com.example.chapter1.model

data class AlbumResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: ResultXX
)