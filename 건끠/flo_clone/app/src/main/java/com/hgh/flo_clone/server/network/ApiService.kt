package com.hgh.flo_clone.server.network

import com.hgh.flo_clone.server.response.album.AlbumResponse
import com.hgh.flo_clone.server.response.albumin.albumInResponse
import com.hgh.flo_clone.server.response.login.auto.autoResponse
import com.hgh.flo_clone.server.response.login.request.loginRequest
import com.hgh.flo_clone.server.response.login.loginResponse
import com.hgh.flo_clone.server.response.sign.SignResponse
import com.hgh.flo_clone.server.response.sign.request.signRequest
import com.hgh.flo_clone.server.response.song.SongResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {

    @GET("/songs")
    suspend fun getSongApi(
    ): Response<SongResponse>

    @GET("/albums")
    suspend fun getAlbumApi(
    ): Response<AlbumResponse>

    @GET("/albums/{albumsId}")
    suspend fun getAlbumInApi(
        @Path("albumsId") albumId : Int
    ): Response<albumInResponse>

    @GET("/users/auto-login")
    suspend fun getAutoLoginApi(
        @Header("X-ACCESS-TOKEN") accessToken: String
    ): Response<autoResponse>

    @POST("/users/login")
    suspend fun postLoginApi(
        @Body request: loginRequest
    ): Response<loginResponse>

    @POST("/users")
    suspend fun postSignApi(
        @Body request: signRequest
    ): Response<SignResponse>
}