package com.wxsoft.triapp.ui.patient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import com.google.gson.Gson
import com.wxsoft.triapp.data.QueryModel
import com.wxsoft.triapp.data.entity.PatientWithDetail
import com.wxsoft.triapp.data.entity.RescueRecord
import com.wxsoft.triapp.data.entity.Row
import com.wxsoft.triapp.data.prefs.SharedPreferenceStorage
import com.wxsoft.triapp.data.remote.PatientApi
import com.wxsoft.triapp.data.remote.RescueRecordApi
import com.wxsoft.triapp.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PatientRescueModel  @Inject constructor (private val patientApi: PatientApi,
                                               private val rescueRecordApi: RescueRecordApi,
                                               override val sharedPreferenceStorage: SharedPreferenceStorage,
                                               override val gon: Gson
): BaseViewModel(sharedPreferenceStorage,gon) {

    var patientId:Int = 0
    set(value) {
        field=value

        getPatientDetail()
        getPatientRescue(QueryModel().apply {
            PageCondition.SortConditions.add(QueryModel.SortCondition("Id"))
            FilterGroup.Rules.add(QueryModel.Rule("patientId",field,QueryModel.Filter.Equal.value))
        })


    }
    val rescueRecord:LiveData<RescueRecord>
    private val loadRescueRecordResult= MediatorLiveData<Row<RescueRecord>>()


    val patient: LiveData<PatientWithDetail>

    private val loadPatientsResult = MediatorLiveData<Row<PatientWithDetail>>()


    init {
        rescueRecord=loadRescueRecordResult.map {
            if(it.Rows.isNullOrEmpty()) RescueRecord(patientId=patientId) else it.Rows[0]
        }

        patient = loadPatientsResult.map {
            it.Rows[0]
        }
    }

    fun getPatientRescue(req:QueryModel){
        rescueRecordApi.read(req)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.Rows?.forEach { item->item.rescues.sortByDescending {
                    resc->resc.updatedAt
                } }
                loadRescueRecordResult.value=it

            },::error)
    }

    fun refresh(){
        getPatientRescue()
    }
    fun getPatientRescue(){
        getPatientRescue(QueryModel().apply {
            PageCondition.SortConditions.let {
                it.clear()
                it.add(QueryModel.SortCondition("Id"))
            }
            FilterGroup.Rules.add(QueryModel.Rule("patientId",patientId,QueryModel.Filter.Equal.value))
        })

    }


    fun getPatientDetail() {
        patientApi.readPatientWithDetailList( QueryModel().apply {
            PageCondition.SortConditions.add(QueryModel.SortCondition("Id"))
            FilterGroup.Rules.add(QueryModel.Rule(field = "Id",value = patientId))
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                loadPatientsResult.value = it

            }, ::error)
    }
}