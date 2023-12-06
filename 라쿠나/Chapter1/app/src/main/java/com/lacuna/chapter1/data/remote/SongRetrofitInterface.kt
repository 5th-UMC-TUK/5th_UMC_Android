package com.lacuna.chapter1.data.remote

import com.lacuna.chapter1.data.AlbumInResponse
import com.lacuna.chapter1.data.AlbumResponse
import com.lacuna.chapter1.data.SongResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SongRetrofitInterface {
    @GET("/songs")
    fun getSongs(): Call<SongResponse>

    @GET("albums")
    fun getAllAlbums(): Call<AlbumResponse>

    @GET("albums/{albumIdx}")
    fun includingSongs(@Path("albumIdx") albumIdx: Int): Call<AlbumInResponse>
}