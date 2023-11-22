package com.hgh.flo_clone.server.repository

import com.hgh.flo_clone.server.response.login.Result
import com.hgh.flo_clone.server.response.login.auto.autoResponse
import com.hgh.flo_clone.server.response.login.request.loginRequest
import com.hgh.flo_clone.server.response.sign.SignResponse
import com.hgh.flo_clone.server.response.sign.request.signRequest

interface UserRepository {

    suspend fun postLogin(info : loginRequest): Result?

    suspend fun postAutoLogin() : autoResponse?

    suspend fun postSign(info :signRequest ) : SignResponse?
}