package com.lacuna.chapter1.data

import com.google.gson.annotations.SerializedName

data class AlbumResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ResultAlbum
)

data class ResultAlbum(
    @SerializedName("albums") val albums: List<Album>
)

data class Album(
    @SerializedName("albumIdx") val albumIdx: Int,
    @SerializedName("coverImgUrl") val coverImgUrl: String,
    @SerializedName("singer") val singer: String,
    @SerializedName("title") val title: String
)
