package com.wxsoft.triapp.ui.main.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import com.google.gson.Gson
import com.wxsoft.triapp.data.QueryModel
import com.wxsoft.triapp.data.entity.*
import com.wxsoft.triapp.data.prefs.SharedPreferenceStorage
import com.wxsoft.triapp.data.remote.PatientApi
import com.wxsoft.triapp.data.remote.StatisticApi
import com.wxsoft.triapp.ui.BaseViewModel
import com.wxsoft.triapp.utils.DateTimeUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class PatientWithDetailModel  @Inject constructor (private val patientApi: PatientApi,
                                                   private val statisticApi: StatisticApi,
                                                   override val sharedPreferenceStorage: SharedPreferenceStorage,
                                                   override val gon: Gson
): BaseViewModel(sharedPreferenceStorage,gon) {

    val greenStatistics:LiveData<ChartGreenWay>
    val deptDistributes:LiveData<DeptDistributes>
    val levelDistributes:LiveData<DeptDistributes>


    private val loadGreenWay = MediatorLiveData<ResponseO<ChartGreenWay>>()
    private val loadDeptDistributes = MediatorLiveData<ResponseO<DeptDistributes>>()
    private val loadLevelDistributes = MediatorLiveData<ResponseO<DeptDistributes>>()

    init {
        greenStatistics=loadGreenWay.map { it.Data?: ChartGreenWay()}
        deptDistributes=loadDeptDistributes.map { it.Data?: DeptDistributes()}
        levelDistributes=loadLevelDistributes.map { it.Data?: DeptDistributes()}
    }

    val times: LiveData<List<String>>

    private val initTimes = MediatorLiveData<List<String>>().apply {
        value = listOf( "今天","昨天", "本周", "本月", "本年","全部")
    }

    val levels: LiveData<List<String>>

    private val initLevels = MediatorLiveData<List<String>>().apply {
        value = listOf("在抢救室","出抢救室", "全部")
    }

    val patients: LiveData<List<PatientWithDetail>>
    private val loadPatientsResult = MediatorLiveData<Row<PatientWithDetail>>()

    private val queryModel = QueryModel().apply {
        PageCondition.SortConditions.add(QueryModel.SortCondition("Id"))
        FilterGroup.Rules.add(
            QueryModel.Rule(
                "station",
                9,
                QueryModel.Filter.Equal.value
            )
        )
//            filterGroup.operate=QueryModel.Filter.And
    }



    var queryString: String = ""
        set(value) {
            field = value
            queryModel.run {
                FilterGroup.Groups.removeAll { item -> item.Rules.any { it.field == "name" || it.field == "ic" || it.field == "blh" } }
                if (!field.isNullOrEmpty()) {

                    FilterGroup.Groups.add(QueryModel.FilterGro()
                        .apply {
                            Rules.add(
                                QueryModel.Rule(
                                    "name",
                                    field,
                                    QueryModel.Filter.Contains.value
                                )
                            )
                            Rules.add(
                                QueryModel.Rule(
                                    "ic",
                                    field,
                                    QueryModel.Filter.Contains.value
                                )
                            )

                            Rules.add(
                                QueryModel.Rule(
                                    "blh",
                                    field,
                                    QueryModel.Filter.Contains.value
                                )
                            )
                            Operate =
                                QueryModel.Filter.Or.value
                            Level = 2

                        })

                }

            }
            getPatientWithDetails()
        }

    init {
        patients = loadPatientsResult.map {
            it.Rows
        }
        times = initTimes.map { it }
        levels = initLevels.map { it }
        getStatistics()
//        getPatientWithDetails()
    }

    fun refresh(){
        getStatistics()
        getPatientWithDetails()
    }
    fun setType(type: String) {
        queryModel.FilterGroup.Rules.removeAll { it.field == "station" }

        when(type){
            "在抢救室" -> {
                queryModel.FilterGroup.Rules.add(
                    QueryModel.Rule(
                        "station",
                        9,
                        QueryModel.Filter.Equal.value
                    )
                )
            }

            "出抢救室" -> {
                queryModel.FilterGroup.Rules.add(
                    QueryModel.Rule(
                        "station",
                        9,
                        QueryModel.Filter.Equal.value
                    )
                )

                queryModel.FilterGroup.Rules.add(
                    QueryModel.Rule(
                        "areaname",
                        "出室",
                        QueryModel.Filter.Equal.value
                    )
                )
            }
        }

        getPatientWithDetails()
    }

    fun setTime(time: String) {
        queryModel.FilterGroup.Rules.removeAll { it.field == "clinicTime" }
        when (time) {
            "今天" -> {

                queryModel.run {
                    FilterGroup.Rules.add(
                        QueryModel.Rule(
                            "clinicTime",
                            DateTimeUtils.dateFormat.format(
                                Calendar.getInstance()
                                    .apply {
                                        set(Calendar.HOUR_OF_DAY, 0)
                                        set(Calendar.SECOND, 0)
                                        set(Calendar.MINUTE, 0)
                                        set(Calendar.MILLISECOND, 0)
                                    }.time
                            ),
                            QueryModel.Filter.GreaterOrEqual.value
                        )
                    )
                    FilterGroup.Rules.add(
                        QueryModel.Rule(
                            "clinicTime",
                            DateTimeUtils.dateFormat.format(
                                Calendar.getInstance().apply {
                                    add(Calendar.DATE, 1)
                                    set(Calendar.HOUR_OF_DAY, 0)
                                    set(Calendar.SECOND, 0)
                                    set(Calendar.MINUTE, 0)
                                    set(Calendar.MILLISECOND, 0)
                                }.time
                            ),
                            QueryModel.Filter.LessOrEqual.value
                        )
                    )
                }
            }

            "昨天" -> {

                queryModel.run {
                    FilterGroup.Rules.add(
                        QueryModel.Rule(
                            "clinicTime",
                            DateTimeUtils.dateFormat.format(
                                Calendar.getInstance()
                                    .apply {
                                        add(Calendar.DATE, -1)
                                        set(Calendar.HOUR_OF_DAY, 0)
                                        set(Calendar.SECOND, 0)
                                        set(Calendar.MINUTE, 0)
                                        set(Calendar.MILLISECOND, 0)
                                    }.time
                            ),
                            QueryModel.Filter.GreaterOrEqual.value
                        )
                    )
                    FilterGroup.Rules.add(
                        QueryModel.Rule(
                            "clinicTime",
                            DateTimeUtils.dateFormat.format(
                                Calendar.getInstance().apply {

                                    set(Calendar.HOUR_OF_DAY, 0)
                                    set(Calendar.SECOND, 0)
                                    set(Calendar.MINUTE, 0)
                                    set(Calendar.MILLISECOND, 0)
                                }.time
                            ),
                            QueryModel.Filter.LessOrEqual.value
                        )
                    )
                }
            }

            "本周" -> {
                queryModel.run {
                    FilterGroup.Rules.add(
                        QueryModel.Rule(
                            "clinicTime",
                            DateTimeUtils.dateFormat.format(
                                Calendar.getInstance().apply {
                                    add(Calendar.DATE, -7)
                                    set(Calendar.HOUR_OF_DAY, 0)
                                    set(Calendar.SECOND, 0)
                                    set(Calendar.MINUTE, 0)
                                    set(Calendar.MILLISECOND, 0)
                                }.time
                            ),
                            QueryModel.Filter.GreaterOrEqual.value
                        )
                    )
                    FilterGroup.Rules.add(
                        QueryModel.Rule(
                            "clinicTime",
                            DateTimeUtils.dateFormat.format(
                                Calendar.getInstance().apply {
                                    set(Calendar.HOUR_OF_DAY, 0)
                                    set(Calendar.SECOND, 0)
                                    set(Calendar.MINUTE, 0)
                                    set(Calendar.MILLISECOND, 0)
                                    add(Calendar.DATE, 1)
                                }.time
                            ),
                            QueryModel.Filter.LessOrEqual.value
                        )
                    )

                }
            }

            "本月" -> {
                queryModel.run {
                    FilterGroup.Rules.add(
                        QueryModel.Rule(
                            "clinicTime",
                            DateTimeUtils.dateFormat.format(
                                Calendar.getInstance().apply {
                                    add(Calendar.MONTH, -1)
                                    set(Calendar.HOUR_OF_DAY, 0)
                                    set(Calendar.SECOND, 0)
                                    set(Calendar.MINUTE, 0)
                                    set(Calendar.MILLISECOND, 0)
                                }.time
                            ),
                            QueryModel.Filter.GreaterOrEqual.value
                        )
                    )
                    FilterGroup.Rules.add(
                        QueryModel.Rule(
                            "clinicTime",
                            DateTimeUtils.dateFormat.format(
                                Calendar.getInstance().apply {
                                    set(Calendar.HOUR_OF_DAY, 0)
                                    set(Calendar.SECOND, 0)
                                    set(Calendar.MINUTE, 0)
                                    set(Calendar.MILLISECOND, 0)
                                    add(Calendar.DATE, 1)
                                }.time
                            ),
                            QueryModel.Filter.LessOrEqual.value
                        )
                    )

                }
            }

            "本年" -> {
                queryModel.run {
                    FilterGroup.Rules.add(
                        QueryModel.Rule(
                            "clinicTime",
                            DateTimeUtils.dateFormat.format(
                                Calendar.getInstance().apply {
                                    add(Calendar.YEAR, -1)
                                    set(Calendar.HOUR_OF_DAY, 0)
                                    set(Calendar.SECOND, 0)
                                    set(Calendar.MINUTE, 0)
                                    set(Calendar.MILLISECOND, 0)
                                }.time
                            ),
                            QueryModel.Filter.GreaterOrEqual.value
                        )
                    )
                    FilterGroup.Rules.add(
                        QueryModel.Rule(
                            "clinicTime",
                            DateTimeUtils.dateFormat.format(
                                Calendar.getInstance().apply {
                                    add(Calendar.DATE, 1)
                                    set(Calendar.HOUR_OF_DAY, 0)
                                    set(Calendar.SECOND, 0)
                                    set(Calendar.MINUTE, 0)
                                    set(Calendar.MILLISECOND, 0)
                                }.time
                            ),
                            QueryModel.Filter.LessOrEqual.value
                        )
                    )

                }
            }
        }

        getPatientWithDetails()
    }

    fun getStatistics(){

        val m= QueryModel().apply {
            PageCondition.SortConditions.clear()
            PageCondition.SortConditions.add(QueryModel.SortCondition("CreatedTime"))
            FilterGroup.Rules.add(
                QueryModel.Rule(
                    "CreatedTime",
                    DateTimeUtils.dateFormat.format(
                        Calendar.getInstance()
                            .apply {

                                add(Calendar.DATE, -1)
                                set(Calendar.HOUR_OF_DAY, 0)
                                set(Calendar.SECOND, 0)
                                set(Calendar.MINUTE, 0)
                                set(Calendar.MILLISECOND, 0)
                            }.time
                    ),
                    QueryModel.Filter.GreaterOrEqual.value
                )
            )
            FilterGroup.Rules.add(
                QueryModel.Rule(
                    "CreatedTime",
                    DateTimeUtils.dateFormat.format(
                        Calendar.getInstance().apply {
                            add(Calendar.DATE, 1)
                            set(Calendar.HOUR_OF_DAY, 0)
                            set(Calendar.SECOND, 0)
                            set(Calendar.MINUTE, 0)
                            set(Calendar.MILLISECOND, 0)
                        }.time
                    ),
                    QueryModel.Filter.LessOrEqual.value
                )
            )
        }
        statisticApi.getGreenWay(m).zipWith(statisticApi.getDept(m)).zipWith(statisticApi.getLevel(m))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                loadGreenWay.value=it.first.first
                loadDeptDistributes.value=it.first.second
                loadLevelDistributes.value=it.second
            }, ::error)
    }

    fun getPatientWithDetails() {
        patientApi.readPatientWithDetailList(queryModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                loadPatientsResult.value = it

            }, ::error)
    }
}