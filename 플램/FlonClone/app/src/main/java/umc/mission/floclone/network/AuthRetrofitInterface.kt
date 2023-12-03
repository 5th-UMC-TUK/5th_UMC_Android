package umc.mission.floclone.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import umc.mission.floclone.network.AuthResponse
import umc.mission.floclone.data.User

interface AuthRetrofitInterface {
    @POST("/users")
    fun singUp(@Body user: User): Call<AuthResponse>

    @POST("/users/login")
    fun login(@Body user: User): Call<AuthResponse>

    @GET("/users/auto-login")
    fun loginAuto(@Header("X-ACCESS-TOKEN") token: String?): Call<AuthResponse>
}