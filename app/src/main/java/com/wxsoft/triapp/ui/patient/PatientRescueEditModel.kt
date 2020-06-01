package com.wxsoft.triapp.ui.patient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.gson.Gson
import com.warriorsfly.core.utils.map
import com.wxsoft.triapp.data.QueryModel
import com.wxsoft.triapp.data.entity.*
import com.wxsoft.triapp.data.prefs.SharedPreferenceStorage
import com.wxsoft.triapp.data.remote.PatientApi
import com.wxsoft.triapp.data.remote.RescueApi
import com.wxsoft.triapp.data.remote.RescueRecordApi
import com.wxsoft.triapp.ui.BaseViewModel
import com.wxsoft.triapp.utils.DateTimeUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class PatientRescueEditModel @Inject constructor(
    private val patientApi: PatientApi,
    private val rescueRecordApi: RescueRecordApi,
    private val rescueApi: RescueApi,
    override val sharedPreferenceStorage: SharedPreferenceStorage,
    override val gon: Gson
) : BaseViewModel(sharedPreferenceStorage, gon) {

    var recordId: Int = 0
    var patientId: Int = 0
    var rescueId: Int = 0
        set(value) {
            field = value

            read(QueryModel().apply {
                FilterGroup.Rules.add(QueryModel.Rule("Id", field, QueryModel.Filter.Equal.value))
            })
        }
    val rescueRecord: LiveData<Rescue>
    private val loadRescueRecordResult = MediatorLiveData<Row<Rescue>>()

    val responseo: LiveData<ResponseO<String>>
    private val submitRescueRecordResult = MediatorLiveData<ResponseO<String>>()

    val click:LiveData<String>
    private val clickView = MediatorLiveData<String>()

    val clickDictionary:LiveData<Int>
    private val onClickDictionary = MediatorLiveData<Int>()

    init {
        rescueRecord=loadRescueRecordResult.map {

            if (it.Rows.isNullOrEmpty()) Rescue(
                recordId = recordId,
                creater = account?.Id?.toInt() ?: 0
            ).apply {
                updatedAt= DateTimeUtils.dateFormat.format(Calendar.getInstance().time)
            } else it.Rows[0]
        }

        responseo=submitRescueRecordResult.map {

            it
        }

        click = clickView.map { it }
        clickDictionary = onClickDictionary.map { it }
    }

    fun read(req: QueryModel) {
        rescueApi.read(req)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                loadRescueRecordResult.value = it
            }, ::error)
    }

    fun submit() {

        if (recordId > 0) {
            rescueRecord.value?.run {
                vital= Vital(patientId=patientId,creater = account?.Id?.toInt()?:0).apply {
                    spo2= this@run.spo2
                    dbp= this@run.dbp
                    sbp= this@run.sbp
                    temp= this@run.temp
                    rr= this@run.rr
                    hrt= this@run.hrt
                }
                rescueRecordApi.read(QueryModel().apply {
                    PageCondition.SortConditions.add(QueryModel.SortCondition("Id"))
                    FilterGroup.Rules.add(QueryModel.Rule( "Id", this@PatientRescueEditModel.recordId,QueryModel.Filter.Equal.value))
                }).flatMap {
                    if(this.Id==0) {
                        it.Rows.forEach { item -> item.rescues.add(this) }
                    }else{
                        it.Rows.forEach { item -> 
                            item.rescues.removeAll{rit-> rit.Id==this.Id }
                        }

                        it.Rows.forEach { item -> item.rescues.add(this) }
                    }

                    return@flatMap rescueRecordApi.update(it.Rows)
                }    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        submitRescueRecordResult.value = it
                    }, ::error)
            }
//            rescueRecord.value?.run {
//                if (Id == 0) {
//                    rescueApi.create(listOf(this))
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe({
//                            submitRescueRecordResult.value = it
//                        }, ::error)
//                } else {
//                    rescueApi.update(listOf(this))
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe({
//                            submitRescueRecordResult.value = it
//                        }, ::error)
//                }
//            }
        } else {
            if (rescueRecord.value == null) return
            val record = rescueRecord.value!!.apply {
                vital= Vital(patientId=patientId,creater = account?.Id?.toInt()?:0,RescueId=this@apply.Id).apply {
                    spo2= this@apply.spo2
                    dbp= this@apply.dbp
                    sbp= this@apply.sbp
                    temp= this@apply.temp
                    rr= this@apply.rr
                    hrt= this@apply.hrt
                }
            }
            patientApi.readPatientWithDetailList(QueryModel().apply {
                PageCondition.SortConditions.add(QueryModel.SortCondition("Id"))
                FilterGroup.Rules.add(QueryModel.Rule(field = "Id", value = patientId))
            }).flatMap {

                var p = it.Rows[0]

                var rescue = RescueRecord(patientId = patientId, unkown = p.unkown).apply {
                    commingWay=p.commingWay
                    rescues.add(record)
                }

                return@flatMap rescueRecordApi.create(listOf(rescue))
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    submitRescueRecordResult.value = it

                }, ::error)
        }
    }


    fun clicking(id: String) {
        clickView.value = id
    }

    fun onClickDict(id: Int) {
        onClickDictionary.value = id
    }
}