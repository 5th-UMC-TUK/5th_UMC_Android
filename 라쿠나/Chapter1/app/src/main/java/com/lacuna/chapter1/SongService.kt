package com.lacuna.chapter1

import android.util.Log
import com.lacuna.chapter1.data.AlbumInResponse
import com.lacuna.chapter1.data.AlbumResponse
import com.lacuna.chapter1.data.SongResponse
import com.lacuna.chapter1.data.remote.SongRetrofitInterface
import com.lacuna.chapter1.view.look.LookView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SongService() {
    private lateinit var lookView: LookView
    private lateinit var albumView: AlbumView
    private lateinit var albumInView: AlbumInView
    fun setLookView(lookView: LookView) {
        this.lookView = lookView
    }
    fun setAlbumView(albumView: AlbumView) {
        this.albumView = albumView
    }
    fun setAlbumInView(albumInView: AlbumInView) {
        this.albumInView = albumInView
    }

    fun getSongs() {
        val songService = getRetrofit().create(SongRetrofitInterface::class.java)
        lookView.onGetSongLoading()

        songService.getSongs().enqueue(object : Callback<SongResponse> {
            override fun onResponse(call: Call<SongResponse>, response: Response<SongResponse>) {
                if (response.isSuccessful && response.code() == 200) {
                    val songResponse: SongResponse = response.body()!!

                    Log.d("SONG-RESPONSE", songResponse.toString())

                    when (val code = songResponse.code) {
                        1000 -> {
                            lookView.onGetSongSuccess(code, songResponse.result!!)
                        }

                        else -> lookView.onGetSongFailure(code, songResponse.message)
                    }
                }
            }

            override fun onFailure(call: Call<SongResponse>, t: Throwable) {
                lookView.onGetSongFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }
    fun getAlbums() {
        val albumService = getRetrofit().create(SongRetrofitInterface::class.java)
        albumService.getAllAlbums().enqueue(object : Callback<AlbumResponse> {
            override fun onResponse(call: Call<AlbumResponse>, response: Response<AlbumResponse>) {
                if (response.isSuccessful && response.code() == 200) {
                    val albumResponse : AlbumResponse = response.body()!!

                    Log.d("ALBUM-RESPONSE", albumResponse.toString())

                    when (val code = albumResponse.code) {
                        1000 -> {
                            albumView.onGetAlbumSuccess(code, albumResponse.result!!)
                        }
                        else -> albumView.onGetAlbumFailure(code, albumResponse.message)
                    }
                }
            }
            override fun onFailure(call: Call<AlbumResponse>, t: Throwable) {
                albumView.onGetAlbumFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }
    fun getAlbumsIn(albumIdx: Int) {
        val albumInService = getRetrofit().create(SongRetrofitInterface::class.java)
        albumInService.includingSongs(albumIdx).enqueue(object : Callback<AlbumInResponse> {
            override fun onResponse(call: Call<AlbumInResponse>, response: Response<AlbumInResponse>) {
                if (response.isSuccessful && response.code() == 200) {
                    val albumInResponse : AlbumInResponse = response.body()!!

                    Log.d("ALBUMIN-RESPONSE", albumInResponse.toString())

                    when (val code = albumInResponse.code) {
                        1000 -> {
                            albumInView.onGetAlbumInSuccess(code, albumInResponse.result[albumIdx])
                        }
                        else -> albumInView.onGetAlbumInFailure(code, albumInResponse.message)
                    }
                }
            }
            override fun onFailure(call: Call<AlbumInResponse>, t: Throwable) {
                albumInView.onGetAlbumInFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }
}