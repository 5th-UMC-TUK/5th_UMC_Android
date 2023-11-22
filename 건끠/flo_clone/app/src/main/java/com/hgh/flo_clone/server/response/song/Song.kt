package com.hgh.flo_clone.server.response.song

data class Song(
    val albumIdx: Int,
    val coverImgUrl: String,
    val singer: String,
    val songIdx: Int,
    val title: String
)