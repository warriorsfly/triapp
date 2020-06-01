package com.wxsoft.triapp.data.entity.rating

/**
 * 评分结果（一题一个记录）
 */
data class SubjectRecord(val Id:Int=0,
                         val recordId:Int,
                         val ratingSubjectId:Int,
                         val selections:List<Int> = emptyList(),
                         val score:Int)