package com.wxsoft.triapp.data.remote

import com.wxsoft.triapp.data.QueryModel
import com.wxsoft.triapp.data.entity.PatientWithDetail
import com.wxsoft.triapp.data.entity.Row
import io.reactivex.Maybe
import retrofit2.http.Body
import retrofit2.http.POST

interface PatientApi {

    /**
     * 登录Jwt
     */
    @POST("api/Admin/V_PatientTriage/Read")
    fun readPatientWithDetailList(@Body req: QueryModel): Maybe<Row<PatientWithDetail>>
}