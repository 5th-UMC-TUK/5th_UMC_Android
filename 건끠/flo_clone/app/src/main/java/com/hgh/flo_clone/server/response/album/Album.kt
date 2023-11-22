package com.hgh.flo_clone.server.response.album

data class Album(
    val albumIdx: Int,
    val coverImgUrl: String,
    val singer: String,
    val title: String
)