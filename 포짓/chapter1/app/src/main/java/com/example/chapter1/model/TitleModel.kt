package com.example.chapter1.model

data class TitleModel(
    val titleText: String,
    val albumInfo: String,
    val titleImgResId: Int,
    val songs: List<SongModel>
)
