package com.wxsoft.triapp.data.entity

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.wxsoft.triapp.BR

data class Dictionary(
    override val Id:Int = 0,
    var ParentId:Int = 0,
    var Key:String? = "",
    var Name:String = ""): BaseObservable(),Entity {

    var TreePathString:String? = ""
    var Memo:String? = ""
    var IsSystem:Boolean? = false
    var IsEnabled:Boolean? = false
    var Sort:Int? = 0
    var CreatedTime:String? = ""

    @Bindable
    @Transient
    var Checked:Boolean=false
        set(value) {
            field=value
            notifyPropertyChanged(BR.Checked)
        }

}