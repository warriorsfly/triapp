package com.wxsoft.triapp.data.entity

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

import com.wxsoft.triapp.BR

data class Vital(
    override val Id: Int = 0,
    val creater: Int = 0,
    val createrTime:String?=null,
    val RescueId:Int=0,
    val patientId:Int=0,
    val CreatedTime: String? = null
) : BaseObservable(), Entity {

    @Bindable
    var spo2: Int = 99
        set(value) {
            field = value
            notifyPropertyChanged(BR.spo2)
        }

    @Bindable
    var dbp: Int = 85
        set(value) {
            field = value
            notifyPropertyChanged(BR.dbp)
        }

    @Bindable
    var sbp: Int = 105
        set(value) {
            field = value
            notifyPropertyChanged(BR.sbp)
        }

    @Bindable
    var temp: Float = 36.5f
        set(value) {
            field = value
            notifyPropertyChanged(BR.temp)
        }

    @Bindable
    var rr: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.rr)
        }

    @Bindable
    var hrt: Int = 70
        set(value) {
            field = value
            notifyPropertyChanged(BR.hrt)
        }

    @Bindable
    var conscious: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.conscious)
        }

}