package com.wxsoft.triapp.ui.common

import dagger.android.support.DaggerAppCompatActivity

open class BaseActivity :DaggerAppCompatActivity(){
    companion object {
        const val CAMERA_PERMISSION_REQUEST=10
        const val UPGRADE_PERMISSION_REQUEST=11
    }
}