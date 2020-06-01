package com.wxsoft.triapp.ui.rating

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.gson.Gson
import com.warriorsfly.core.utils.map
import com.wxsoft.triapp.data.QueryModel
import com.wxsoft.triapp.data.entity.Row
import com.wxsoft.triapp.data.entity.rating.Rating
import com.wxsoft.triapp.data.entity.rating.RatingRecord
import com.wxsoft.triapp.data.entity.rating.RatingResult
import com.wxsoft.triapp.data.prefs.SharedPreferenceStorage
import com.wxsoft.triapp.data.remote.RatingApi
import com.wxsoft.triapp.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class RatingViewModel @Inject constructor(
    private val ratingApi: RatingApi,
//    private val tints: IntArray,
    override val sharedPreferenceStorage: SharedPreferenceStorage,
    override val gon: Gson
) : BaseViewModel(sharedPreferenceStorage, gon) {

    var patientId = 0
        set(value) {
            field = value
            loadRecords()
            loadRating()
        }

    fun refresh() {
        if (patientId != 0) {
            loadRecords()
        }
    }

    /**
     * 场景化数据
     */
    val results: LiveData<List<RatingRecord>>
    private val loadRatingResult = MediatorLiveData<Row<RatingRecord>>()

    init {
        results = loadRatingResult.map { it.Rows }
    }

    /**
     * 评分列表
     */
    val ratings: LiveData<List<Rating>>
    private val loadResult = MediatorLiveData<Row<Rating>>()

    init {
        ratings = loadResult.map { it.Rows }
    }

    private fun loadRecords() {
        disposable.add(
            ratingApi.readRecords(QueryModel().apply {
                FilterGroup.Rules.add(QueryModel.Rule("patientId", patientId))
            }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::doRecords, ::error)
        )
    }

    private fun doRecords(response: Row<RatingRecord>) {

        loadRatingResult.value = response
    }

    //
    private fun loadRating() {
        disposable.add(
            ratingApi.readRatings(QueryModel().apply {
                PageCondition.SortConditions.add(QueryModel.SortCondition("Id"))
            }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::doLoadRating, ::error)
        )
    }

    private fun doLoadRating(response: Row<Rating>) {

        loadResult.value = response
    }
}