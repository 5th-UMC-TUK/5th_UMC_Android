package com.lacuna.chapter1

import com.lacuna.chapter1.data.FloChartResult
import com.lacuna.chapter1.data.ResultAlbum

interface AlbumView {
    fun onGetAlbumSuccess(code: Int, result: ResultAlbum)
    fun onGetAlbumFailure(code: Int, message: String)
}