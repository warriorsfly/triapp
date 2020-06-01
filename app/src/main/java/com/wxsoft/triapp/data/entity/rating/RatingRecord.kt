package com.wxsoft.triapp.data.entity.rating

/**
 * 评分
 */
data class RatingRecord(
    val Id: Int = 0,
    val patientId: Int,
    val ratingId: Int,
    val ratingName: String,
    val creater: Int,
    val createrName: String,
    /**
     * 1低危2中危3高危
     */
    var level: Int = 0,
    var CreatedTime: String = "",
    var score: Int = 0
){

    var subjects: List<SubjectRecord> = emptyList()
}