package com.wxsoft.triapp.ui.patient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.gson.Gson
import com.warriorsfly.core.utils.map
import com.wxsoft.triapp.data.entity.Report
import com.wxsoft.triapp.data.entity.ReportDetail
import com.wxsoft.triapp.data.entity.ResponseO
import com.wxsoft.triapp.data.prefs.SharedPreferenceStorage
import com.wxsoft.triapp.data.remote.ReportApi
import com.wxsoft.triapp.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LisModel @Inject constructor(
    private val lisApi: ReportApi,
    override val sharedPreferenceStorage: SharedPreferenceStorage,
    override val gon: Gson
) : BaseViewModel(sharedPreferenceStorage, gon) {
    var flag: Int = 1

    var blh:String = ""
    set(value) {
        field=value
        loadReports(field)
    }


    fun refresh(){
        loadReports(blh)
    }
    //检验检查报告
    val reports: LiveData<List<Report>>
    private val loadReportResult = MediatorLiveData<ResponseO<List<Report>>>()


    val details: LiveData<List<ReportDetail>>
    private val loadDetailResult = MediatorLiveData<ResponseO<List<ReportDetail>>>()

    init {
        reports = loadReportResult.map {
            it.Data ?: emptyList()
        }

        details = loadDetailResult.map {
            it.Data ?: emptyList()
        }
    }

    fun loadReports(blh:String){
        disposable.add(
            lisApi.readReport(blh,flag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    loadReportResult.value=it
                },::error)
        )
    }

    fun loadDetails(applyNo: String) {
        lisApi.readReportDetail(applyNo,flag)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                loadDetailResult.value=it
            },::error)
    }
}