package com.hgh.flo_clone.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrentMusicModel(
    val title: String,
    val singer : String,
    val imgPath : String = "",
    val isTitle : Boolean = false,
    var currentPosition : Int = 0,
    var isPlaying : Boolean = false
): Parcelable

