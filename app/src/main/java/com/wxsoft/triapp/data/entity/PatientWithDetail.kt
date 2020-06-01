package com.wxsoft.triapp.data.entity

import android.graphics.Color
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.wxsoft.triapp.BR

data class PatientWithDetail(
    override val Id: Int = 0,
    var name: String = "",
    var unkown: Boolean = false,
    var ethnic: String = "汉族",
    var vc: String = "",
    var ic: String = "",
    var ssc: String = "",
    var gender: Int = 0,
    var age: Int = 0,
    var birthday: String = "",
    var expense: String = "",
    var ybdm: String? = null,
    var onset: String? = null,
    var linkMan: String? = null,
    var linkPhone: String? = null,
    var wardId: String? = null,
    var address: String? = null,
    var commingWay: Int = 3,
    var alarmedAt: String? = null,
    var arrivedAt: String? = null,
    var greenPassageWay: String? = null,
    var medicalHistory: String? = null,
    var allergy: String? = null,
    var allergen: String? = null,
    var createdId: Int = 0,
    var blh: String? = null,
    var clinicTime: String? = null,
    var triageTime: String? = null,
    var triageConsuming: String? = null,
    var vitalId: Int = 0,
    var diagnosis: String? = null,
    var computedStation: String? = null,
    var computedDept: String? = null,
    var computedLevel: String? = null,
    var level: String? = null,
    var reason: String? = null,
    var dept: String? = null,
    var CreatedTime: String? = null,
    var triageId: Int? = null,
    var station: String? = null,
    var triageRatingRecords: String? = null,
    var complaint: String? = null,
    var estimateTime: String? = null,
    var triagedCount: String? = null,
    var status: Int? = null,
    var receiveStatus: Int? = null,
    var groupId: Int = 0,
    var treatmentStatus: Int = 0,
    var againAssess: Boolean = false,
    var groupInjuryNo: String? = null,
    var triageLastUpdateTime: String? = null,
    var FristStation: String? = null,
    var FristLevel: String? = null,
    var FristDept: String? = null,
    var ageUnit: String? = null,
    var epidemicSituation: String? = null,
    var proId: Int? = null,
    var proCreateTime: String? = null,
    var bedName: String? = null,
    var areaname: String? = null
) : Entity, BaseObservable() {


    @get:Bindable
    val ageUni:String
        get() {
            var lc = when (ageUnit) {

                "1" -> "岁"
                else -> "月"
            }
            notifyPropertyChanged(BR.ageUni)
            return lc
        }


    @get:Bindable
    val commingType:String
        get() {
            var lc = when (commingWay) {

                1 -> "110"
                2 -> "120"
                3 -> "步行"
                4 -> "搀扶"
                5 -> "轮椅"
                6 -> "平车"
                else -> "其他"
            }
            notifyPropertyChanged(BR.commingType)
            return lc
        }
    @get:Bindable
    val textColor: Int
        get() {
            var lc = when (level) {

                "1", "2", "4" -> Color.parseColor("#ffffff")
                else -> Color.parseColor("#000000")
            }
            notifyPropertyChanged(BR.textColor)
            return lc
        }

    @get:Bindable
    val levelColor: Int
        get() {
            var lc = when (level) {

                "1" -> Color.parseColor("#e9533a")
                "2" -> Color.parseColor("#ffa500")
                "3" -> Color.parseColor("#ffff00")
                "4" -> Color.parseColor("#70ad47")
                else -> Color.parseColor("#ffffff")
            }
            notifyPropertyChanged(BR.levelColor)
            return lc
        }

    @get:Bindable
    val levelDescription: String
        get() {
            var ld = when (level) {

                "1" -> "I级"
                "2" -> "II级"
                "3" -> "III级"
                "4" -> "IV级"
                else -> "未"
            }
            notifyPropertyChanged(BR.levelDescription)
            return ld
        }

}