package com.wxsoft.triapp.data.entity

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.wxsoft.triapp.BR

data class User (
    val Id:String

): BaseObservable(){

    @Bindable
    var UserName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.UserName)
        }

    @Bindable
    var NickName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.NickName)
        }


    @Bindable
    var Roles = mutableListOf<String>()
        set(value) {
            field = value
            notifyPropertyChanged(BR.Roles)
        }
}