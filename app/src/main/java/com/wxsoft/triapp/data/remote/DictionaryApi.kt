package com.wxsoft.triapp.data.remote

import com.wxsoft.triapp.data.QueryModel
import com.wxsoft.triapp.data.entity.Dictionary
import com.wxsoft.triapp.data.entity.Row
import com.wxsoft.triapp.data.entity.User
import io.reactivex.Maybe
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DictionaryApi {

    @POST("/api/Admin/Dictionary/Read")
    fun read(@Body req: QueryModel): Maybe<Row<Dictionary>>


    @GET("api/Identity/Profile")
    fun getProfile(): Maybe<User>
}