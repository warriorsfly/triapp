package com.wxsoft.triapp.data.remote

import com.wxsoft.triapp.data.QueryModel
import com.wxsoft.triapp.data.entity.RescueRecord
import com.wxsoft.triapp.data.entity.ResponseO
import com.wxsoft.triapp.data.entity.Row
import com.wxsoft.triapp.data.entity.rating.Rating
import com.wxsoft.triapp.data.entity.rating.RatingRecord
import io.reactivex.Maybe
import retrofit2.http.Body
import retrofit2.http.POST

interface RatingApi {

    @POST("/api/Admin/RatingRecord/Read")
    fun readRecords(@Body req: QueryModel): Maybe<Row<RatingRecord>>

    @POST("/api/Admin/RatingRecord/Create")
    fun create(@Body items: List<RatingRecord>): Maybe<ResponseO<List<RatingRecord>>>

    @POST("/api/Admin/RatingRecord/Update")
    fun update(@Body items: List<RatingRecord>): Maybe<ResponseO<List<RatingRecord>>>

    /**
     *
     */
    @POST("/api/Admin/Rating/Read")
    fun readRatings(@Body req: QueryModel): Maybe<Row<Rating>>

}