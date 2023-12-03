package umc.mission.retrofitex

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubInterface {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String?): Call<RepoResponse>
}