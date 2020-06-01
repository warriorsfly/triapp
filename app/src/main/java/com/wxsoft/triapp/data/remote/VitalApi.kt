package com.wxsoft.triapp.data.remote

import com.wxsoft.triapp.data.QueryModel
import com.wxsoft.triapp.data.entity.Dictionary
import com.wxsoft.triapp.data.entity.Row
import io.reactivex.Maybe
import retrofit2.http.Body
import retrofit2.http.POST

interface VitalApi {

    @POST("/api/Admin/VitalRecord/Read")
    fun read(@Body req: QueryModel): Maybe<Row<Dictionary>>
}