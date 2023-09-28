package com.hgh.flo_clone.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlbumModel(
    val albumTitle : String,
    val albumSinger: String,
    val albumOutline : String,
    val musicList: List<MusicModel>,
) : Parcelable