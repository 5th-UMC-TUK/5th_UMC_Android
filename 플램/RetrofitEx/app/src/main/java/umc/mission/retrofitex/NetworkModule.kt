package umc.mission.retrofitex

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://api.github.com/"

class NetworkModule {
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(18, TimeUnit.SECONDS)
        .writeTimeout(18, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var api = retrofit.create(GitHubInterface::class.java)

    fun getAllRepos(id: String): RepoResponse? {
        val repos = api.listRepos(id)
        return try {
            val result = repos.execute()
            Log.d("result", "결과: ${result.code()}")
            result.body()!!
        } catch (e: Exception) {
            Log.d("error", "error: ${e.message}")
            null
        }
    }
}

fun getRetrofit(): Retrofit {
    val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()
    return retrofit
}