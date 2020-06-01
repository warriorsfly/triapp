package com.wxsoft.triapp.ui.rating

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.gson.Gson
import com.warriorsfly.core.result.Event
import com.warriorsfly.core.utils.map
import com.wxsoft.triapp.data.QueryModel
import com.wxsoft.triapp.data.entity.Row
import com.wxsoft.triapp.data.entity.rating.Rating
import com.wxsoft.triapp.data.entity.rating.RatingRecord
import com.wxsoft.triapp.data.entity.rating.SubjectRecord
import com.wxsoft.triapp.data.prefs.SharedPreferenceStorage
import com.wxsoft.triapp.data.remote.RatingApi
import com.wxsoft.triapp.ui.BaseViewModel
import com.wxsoft.triapp.utils.DateTimeUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject


class RatingSubjectViewModel @Inject constructor(
    private val ratingApi: RatingApi,
    override val sharedPreferenceStorage: SharedPreferenceStorage,
    override val gon: Gson
) : BaseViewModel(sharedPreferenceStorage,gon) {

    var patientId = 0

    var ratingName = ""
    var ratingId = 0
        set(value) {
            field = value
            if (field == 0) return
            loadRating()
        }

    var therecordId = 0

    var recordId = 0
        set(value) {
            field = value
            therecordId = field
            if (value == 0) return
            loadRecord()
        }

    val rating: LiveData<Rating>
    private val loadRatingResult = MediatorLiveData<Row<Rating>>()

    val savingResult: LiveData<Boolean>
    private val savingRatingResult = MediatorLiveData<Boolean>()
    val record: LiveData<RatingRecord>
    private val loadRecordResult = MediatorLiveData<Row<RatingRecord>>()

    init {

        rating = loadRatingResult.map { it.Rows?.getOrNull(0) ?: Rating() }
        record = loadRecordResult.map {
            it.Rows?.getOrNull(0) ?: RatingRecord(
                patientId = patientId,
                ratingId = ratingId,
                ratingName = ratingName,
                creater = account?.Id?.toInt() ?: 0,
                createrName = account?.NickName ?: "",
                CreatedTime = DateTimeUtils.formatter.format( Calendar.getInstance().time)
            )
        }
        savingResult = savingRatingResult.map { it }
    }

    private fun loadRecord() {

        disposable.add(
            ratingApi.readRecords(QueryModel().apply {
                PageCondition.SortConditions.add(QueryModel.SortCondition("Id"))
                FilterGroup.Rules.add(QueryModel.Rule("Id", recordId))
            })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::doRecord, ::error)
        )

    }

    private fun loadRating() {
        disposable.add(
            ratingApi.readRatings(QueryModel().apply {
                PageCondition.SortConditions.add(QueryModel.SortCondition("Id"))
                FilterGroup.Rules.add(QueryModel.Rule("Id", ratingId))
            })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::doSubjects, ::error)
        )

    }

    private fun checkSavable(): Boolean {

        if (rating.value == null) return false
        val errorOne =
            rating.value?.subjects?.firstOrNull { it.options.size > 1 && it.options.none { op -> op.checked } }

        errorOne?.let {
            messageAction.value = Event(it.name + "未选择")
            return false
        }
        return true
    }

    private fun doSubjects(response: Row<Rating>) {

        response.Rows.forEach {
            it.subjects.forEach {
                subject->
                subject.options.removeAll { op->op.score==-999 }
            }
        }
//        response.result?.apply {
//            max=subjects.size
//        }
        record.value?.subjects?.forEach {

            val selectedOptions = it.selections

//
            val ops =
                response.Rows.getOrNull(0)?.subjects?.first { sub -> sub.Id == it.ratingSubjectId }?.options

            selectedOptions.forEach { id ->
                ops?.firstOrNull { op -> op.Id == id }?.checked = true
            }

        }

        response.Rows?.getOrNull(0)?.refreshScore()
        loadRatingResult.value = response
    }

    private fun doRecord(response: Row<RatingRecord>) {
        loadRecordResult.value = response

        ratingId = response?.Rows.getOrNull(0)?.ratingId ?: 0
    }

    fun deleteRecord() {
//        if (recordId.isNotEmpty()) {
//            disposable.add(
//                ratingApi.delete(recordId)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(
//                        {
//                            recordId = ""
//                            rating.value?.score = 0
//                            messageAction.value = Event("删除成功")
//                            savingRatingResult.value = true
//                        },
//                        ::error
//                    )
//            )
//        }
    }

    fun saveRecord() {
        if (checkSavable()) {
            //前面对rating.value进行判空
            val ratingRecord = if (recordId == 0) RatingRecord(
                creater = account?.Id?.toInt()?:0,
                createrName = account?.NickName?:"",
                patientId = patientId,
                ratingId = rating.value!!.Id,
                ratingName = rating.value!!.name,
                score = rating.value!!.score,
                CreatedTime = DateTimeUtils.formatter.format( Calendar.getInstance().time)
            ) else record.value!!.apply { score = rating.value!!.score }

            ratingRecord.subjects = rating.value?.subjects?.map {
                SubjectRecord(
                    recordId = recordId,
                    ratingSubjectId = it.Id,
                    selections = it.options.filter { op -> op.checked }.map{ o -> o.Id },
                    score = it.options.filter { op -> op.checked }.sumBy { op -> op.score }
                )
            }!!

            disposable.add(
                (if(ratingRecord.Id==0)ratingApi.create(listOf(ratingRecord)) else ratingApi.update(
                    listOf(ratingRecord)))

                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            messageAction.value = Event("保存成功")
                            savingRatingResult.value = true
                        },
                        ::error
                    )
            )
        }
    }

}