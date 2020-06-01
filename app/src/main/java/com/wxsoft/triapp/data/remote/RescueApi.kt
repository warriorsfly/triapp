package com.wxsoft.triapp.data.remote

import com.wxsoft.triapp.data.QueryModel
import com.wxsoft.triapp.data.entity.Rescue
import com.wxsoft.triapp.data.entity.RescueRecord
import com.wxsoft.triapp.data.entity.ResponseO
import com.wxsoft.triapp.data.entity.Row
import io.reactivex.Maybe
import retrofit2.http.Body
import retrofit2.http.POST

interface RescueApi {

    @POST("/api/Admin/Rescue/Read")
    fun read(@Body req: QueryModel): Maybe<Row<Rescue>>

    @POST("/api/Admin/Rescue/Create")
    fun create(@Body items: List<Rescue>): Maybe<ResponseO<String>>

    @POST("/api/Admin/Rescue/Update")
    fun update(@Body items: List<Rescue>): Maybe<ResponseO<String>>
}