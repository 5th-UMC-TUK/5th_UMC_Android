package com.posite.retrofitex.network

import android.util.Log
import com.posite.retrofitex.BuildConfig
import com.posite.retrofitex.model.RepoResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class GithubRepository {
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

    private var api = retrofit.create(GithubRetrofitInterface::class.java)

    fun getAllRepos(id: String): RepoResponse? {
        val songs = api.listRepos(id)
        return try {
            val result = songs.execute()
            Log.d("result", "posite repo들 결과: ${result.code()}")
            result.body()!!
        } catch (e: Exception) {
            Log.d("error", "posite repo들 error: ${e.message}")
            null
        }
    }
}