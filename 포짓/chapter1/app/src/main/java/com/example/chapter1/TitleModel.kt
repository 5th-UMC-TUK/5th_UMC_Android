package com.example.chapter1

data class TitleModel(
    val titleText: String,
    val albumInfo: String,
    val titleImgResId: Int,
    val songs: List<SongModel>
)
