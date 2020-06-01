package com.wxsoft.triapp.data.entity

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

import com.wxsoft.triapp.BR

data class RescueRecord(
    override val Id: Int = 0,
    val patientId: Int,
    val unkown: Boolean = true,
    val CreatedTime: String? = null
) : BaseObservable(), Entity {
    @Bindable
    var isGreenPass: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.isGreenPass)
        }

    @Bindable
    var commingWay: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.commingWay)
        }

    @Bindable
    var inhouseWay: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.inhouseWay)
        }

    @Bindable
    var isHeartInfarction: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.isHeartInfarction)
        }

    @Bindable
    var isHeartFailure: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.isHeartFailure)
        }

    @Bindable
    var isRespiratoryWeakness: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.isRespiratoryWeakness)
        }

    @Bindable
    var isBrainInj: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.isBrainInj)
        }

    @Bindable
    var isMultipleInj: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.isMultipleInj)
        }

    @Bindable
    var isStroke: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.isStroke)
        }

    @Bindable
    var complaint: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.complaint)
        }

    @Bindable
    var diagnosis: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.diagnosis)
        }

    @Bindable
    var ecg: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.ecg)
        }

    @Bindable
    var mri: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.mri)
        }

    @Bindable
    var xray: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.xray)
        }

    @Bindable
    var ct: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.ct)
        }

    @Bindable
    var test: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.test)
        }

    @Bindable
    var rescues: MutableList<Rescue> = mutableListOf()
        set(value) {
            field = value
            notifyPropertyChanged(BR.rescues)
        }
}