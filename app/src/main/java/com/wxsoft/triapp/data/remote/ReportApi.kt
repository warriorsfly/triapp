package com.wxsoft.triapp.data.remote

import com.wxsoft.triapp.data.entity.Report
import com.wxsoft.triapp.data.entity.ReportDetail
import com.wxsoft.triapp.data.entity.ResponseO
import io.reactivex.Maybe
import retrofit2.http.POST
import retrofit2.http.Query

interface ReportApi {

    @POST("/api/Admin/Patient/GetInspectionExamine")
    fun readReport(@Query("blh")blh:String,@Query("flag")flag:Int): Maybe<ResponseO<List<Report>>>

    @POST("/api/Admin/Patient/GetInspectionExamineDetail")
    fun readReportDetail(@Query("applyNo")applyNo:String,@Query("flag")flag:Int): Maybe<ResponseO<List<ReportDetail>>>
}