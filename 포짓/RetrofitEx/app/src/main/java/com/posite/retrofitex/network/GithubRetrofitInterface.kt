package com.posite.retrofitex.network

import com.posite.retrofitex.model.RepoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface GithubRetrofitInterface {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String?): Call<RepoResponse>
}