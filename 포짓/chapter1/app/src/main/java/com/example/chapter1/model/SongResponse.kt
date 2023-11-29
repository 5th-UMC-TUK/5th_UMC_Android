package com.example.chapter1.model

data class SongResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: ResultX
)