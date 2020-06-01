package com.wxsoft.triapp.data.entity.rating

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.annotations.SerializedName
import com.wxsoft.triapp.BR

/**
 * 评分选项
 */
data class Option(val Id:Int,
                  val subjectId:String,
                  val name:String,
                  val score:Int,
                  val index:Int):BaseObservable(){
    @Transient
    @get:Bindable
    var checked:Boolean=false
    set(value) {
        field=value
        notifyPropertyChanged(BR.checked)
    }

}