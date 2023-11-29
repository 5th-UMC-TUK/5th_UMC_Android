package com.example.chapter1.network

import android.util.Log
import com.example.chapter1.BuildConfig
import com.example.chapter1.model.AlbumIncludingSongResponse
import com.example.chapter1.model.AlbumResponse
import com.example.chapter1.model.SongResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class SongRepository {
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(18, TimeUnit.SECONDS)
        .writeTimeout(18, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    private val retrofit = Retrofit.Builder().baseUrl(BuildConfig.ipApi)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var api = retrofit.create(SongRetrofitInterface::class.java)

    fun getAllSongs(): SongResponse? {
        val songs = api.getAllSongs()
        return try {
            val result = songs.execute()
            Log.d("result", "전곡 결과: ${result.code()}")
            result.body()!!
        } catch (e: Exception) {
            Log.d("error", "전곡 error: ${e.message}")
            null
        }
    }

    fun getAllAlbums(): AlbumResponse? {
        val albums = api.getAllAlbums()
        return try {
            val result = albums.execute()
            Log.d("result", "전곡 결과: ${result.code()}")
            result.body()!!
        } catch (e: Exception) {
            Log.d("error", "전곡 error: ${e.message}")
            null
        }
    }

    fun getIncludingSongs(albumIdx: Int): AlbumIncludingSongResponse? {
        val albums = api.includingSongs(albumIdx)
        return try {
            val result = albums.execute()
            Log.d("result", "전곡 결과: ${result.code()}")
            result.body()!!
        } catch (e: Exception) {
            Log.d("error", "전곡 error: ${e.message}")
            null
        }
    }
}