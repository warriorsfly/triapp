package com.wxsoft.triapp.data.remote

import com.wxsoft.triapp.data.QueryModel
import com.wxsoft.triapp.data.entity.*
import io.reactivex.Maybe
import retrofit2.http.Body
import retrofit2.http.POST

interface StatisticApi {

    @POST("/api/Admin/Dashboard/GreenPassageWayPersonStatistic")
    fun getGreenWay(@Body req: QueryModel): Maybe<ResponseO<ChartGreenWay>>

    @POST("api/Admin/Dashboard/DeptDistributeStatistic")
    fun getDept(@Body req: QueryModel): Maybe<ResponseO<DeptDistributes>>

    @POST("api/Admin/Dashboard/LevelDistributeStatistic")
    fun getLevel(@Body req: QueryModel): Maybe<ResponseO<DeptDistributes>>
}