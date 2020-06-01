package com.wxsoft.triapp.data.remote

import com.wxsoft.triapp.data.QueryModel
import com.wxsoft.triapp.data.entity.RescueRecord
import com.wxsoft.triapp.data.entity.ResponseO
import com.wxsoft.triapp.data.entity.Row
import io.reactivex.Maybe
import retrofit2.http.Body
import retrofit2.http.POST

interface RescueRecordApi {

    @POST("/api/Admin/RescueRecord/Read")
    fun read(@Body req: QueryModel): Maybe<Row<RescueRecord>>

    @POST("/api/Admin/RescueRecord/Create")
    fun create(@Body items: List<RescueRecord>): Maybe<ResponseO<String>>

    @POST("/api/Admin/RescueRecord/Update")
    fun update(@Body items: List<RescueRecord>): Maybe<ResponseO<String>>
}