package com.hgh.flo_clone.data

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Parcelable
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeBannerModel(
    val title:String,
    val songTitle1 : String,
    val songTitle2 : String,
    val songSinger1 : String,
    val songSinger2 : String,
    @ColorRes val color: Int
): Parcelable
