package com.wxsoft.triapp.data.entity.rating

/**
 * 评分
 */
data class RatingResult(val id:String,
                        val patientId:String,
                        val ratingId:String,

                        val ratingName:String,
                        val score:Int,
                        val resultLevel:Int,
                        val rating:String,
                        val CreatedTime:String?,
                        val createrName:String?,
                        val modifiedDate:String?,
                        val modifierName:String?,
                        val resultGrade:Grade?)