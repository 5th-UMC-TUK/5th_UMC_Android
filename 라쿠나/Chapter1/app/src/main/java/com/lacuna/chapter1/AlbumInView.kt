package com.lacuna.chapter1

import com.lacuna.chapter1.data.AlbumInResponse
import com.lacuna.chapter1.data.ResultAlbumIn

interface AlbumInView {
    fun onGetAlbumInSuccess(code: Int, result: ResultAlbumIn)
    fun onGetAlbumInFailure(code: Int, message: String)
}