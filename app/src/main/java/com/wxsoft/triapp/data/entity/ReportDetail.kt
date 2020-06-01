package com.wxsoft.triapp.data.entity

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

import com.wxsoft.triapp.BR

data class ReportDetail(
    val ItemCode: String = "",
    val ItemName: String = "",
    val Result: String = "",
    val Highlowflag: String? = null,
    val Referncerange: String? = null
)