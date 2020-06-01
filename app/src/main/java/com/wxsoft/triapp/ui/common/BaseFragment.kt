package com.wxsoft.triapp.ui.common

import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment(){
    companion object{
        const val ARG_CREATE_ITEM_CODE=14
        const val ARG_UPDATE_ITEM_CODE=15
        const val REQUEST_CODE_RESCUE_DETAIL=16




        const val PATIENT_ID = "PATIENT_ID"
    }
}