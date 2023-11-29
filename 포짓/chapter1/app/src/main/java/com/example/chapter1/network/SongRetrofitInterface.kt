package com.example.chapter1.network

import com.example.chapter1.model.AlbumIncludingSongResponse
import com.example.chapter1.model.AlbumResponse
import com.example.chapter1.model.SongResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SongRetrofitInterface {
    @GET("songs")
    fun getAllSongs(): Call<SongResponse>

    @GET("albums")
    fun getAllAlbums(): Call<AlbumResponse>

    @GET("albums/{albumIdx}")
    fun includingSongs(@Path("albumIdx") albumIdx: Int): Call<AlbumIncludingSongResponse>
}