package com.wxsoft.triapp.data.remote

import com.wxsoft.triapp.data.entity.JwtRequest
import com.wxsoft.triapp.data.entity.ResponseO
import com.wxsoft.triapp.data.entity.User
import io.reactivex.Maybe
import retrofit2.http.Body
import retrofit2.http.POST

interface IdentityApi {

    /**
     * 登录Jwt
     */
    @POST("api/Identity/Jwtoken")
    fun getJwt(@Body req:JwtRequest): Maybe<ResponseO<String>>

}