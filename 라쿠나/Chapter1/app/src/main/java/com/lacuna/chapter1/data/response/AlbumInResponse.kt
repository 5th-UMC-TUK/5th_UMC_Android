package com.lacuna.chapter1.data

import com.google.gson.annotations.SerializedName

data class AlbumInResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: List<ResultAlbumIn>
)

data class ResultAlbumIn(
    @SerializedName("isTitleSong") val isTitleSong: String,
    @SerializedName("singer") val singer: String,
    @SerializedName("songIdx") val songIdx: Int,
    @SerializedName("title") val title: String
)