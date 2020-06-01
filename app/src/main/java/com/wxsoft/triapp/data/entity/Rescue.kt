package com.wxsoft.triapp.data.entity

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

import com.wxsoft.triapp.BR

data class Rescue(
    override val Id: Int = 0,
    val creater: Int = 0,
    val updater: Int = 0,
    val recordId: Int = 0,
    val CreatedTime: String? = null
) : BaseObservable(), Entity {
    @Bindable
    var updatedAt: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.updatedAt)
        }

    @Bindable
    var vital: Vital? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.vital)
        }

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
    var conscious: Int = 1
        set(value) {
            field = value
            notifyPropertyChanged(BR.conscious)
        }

    @Bindable
    var pupilLeft: String? = "++"
        set(value) {
            field = value
            notifyPropertyChanged(BR.pupilLeft)
        }

    @Bindable
    var pupilRight: String? = "++"
        set(value) {
            field = value
            notifyPropertyChanged(BR.pupilRight)
        }

    @Bindable
    var pupilDiameterLeft: String? = "3.0"
        set(value) {
            field = value
            notifyPropertyChanged(BR.pupilDiameterLeft)
        }

    @Bindable
    var pupilDiameterRight: String? = "3.0"
        set(value) {
            field = value
            notifyPropertyChanged(BR.pupilDiameterRight)
        }

    @Bindable
    var bloodSugar: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.bloodSugar)
        }

    @Bindable
    var decubitus: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.decubitus)
        }

    @Bindable
    var involume: Int = 250
        set(value) {
            field = value
            notifyPropertyChanged(BR.involume)
        }

    @Bindable
    var outvolume: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.outvolume)
        }

    @Bindable
    var involumeName: String = "氯化钠"
        set(value) {
            field = value
            notifyPropertyChanged(BR.involumeName)
        }

    @Bindable
    var outvolumeName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.outvolumeName)
        }

    @Bindable
    var curemeasures: MutableList<String> = mutableListOf()
        set(value) {
            field = value
            notifyPropertyChanged(BR.curemeasures)
        }

    @Bindable
    var curemeasure: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.curemeasure)
        }

    @Bindable
    var curemeasure2: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.curemeasure2)
        }

    @Bindable
    var sign: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.sign)
        }
}