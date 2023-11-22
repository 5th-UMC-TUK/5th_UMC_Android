package com.hgh.flo_clone.server.repository

import android.util.Log
import com.hgh.flo_clone.server.network.ApiService
import com.hgh.flo_clone.server.response.login.Result
import com.hgh.flo_clone.server.response.login.auto.autoResponse
import com.hgh.flo_clone.server.response.login.request.loginRequest
import com.hgh.flo_clone.server.response.sign.SignResponse
import com.hgh.flo_clone.server.response.sign.request.signRequest
import com.hgh.flo_clone.util.UserCode
import com.hgh.flo_clone.util.getJwt
import com.hgh.flo_clone.util.saveJwt
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultUserRepository(
    private val provideApiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
):UserRepository {
    override suspend fun postLogin(info: loginRequest): Result? = withContext(ioDispatcher) {
        val response = provideApiService.postLoginApi(info)
        if (response.isSuccessful){
            saveJwt(response.body()?.result?.jwt ?:"")
            response.body()!!.result
        }else{
            null
        }
    }

    override suspend fun postAutoLogin(): autoResponse? = withContext(ioDispatcher) {
        val response = provideApiService.getAutoLoginApi(getJwt()?:" ")
        if (response.isSuccessful){
            when(response.body()?.code){
                2002 -> Log.d("API_E","2002")
                2001 -> Log.d("API_E","2001")
            }
            response.body()!!
        }else{
            null
        }
    }

    override suspend fun postSign(info: signRequest): SignResponse? = withContext(ioDispatcher) {
        val response = provideApiService.postSignApi(info)
        if (response.isSuccessful){
            when(response.body()?.code){
                2017 -> Log.d("API_E","2017")
                2016 -> Log.d("API_E","2016")
                2000 -> Log.d("API_E","2000")
            }
            response.body()
        }else{
            null
        }
    }
}