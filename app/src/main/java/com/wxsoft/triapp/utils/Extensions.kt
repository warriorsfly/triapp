package com.wxsoft.triapp.utils

import android.content.Intent
import com.wxsoft.triapp.ui.common.BaseFragment
import com.wxsoft.triapp.ui.common.FragmentArgs
import com.wxsoft.triapp.ui.widgets.FragmentContainerActivity

inline fun BaseFragment.launch(clazz:Class<out BaseFragment?>,args: FragmentArgs? = null){
    val intent =
        Intent(activity, FragmentContainerActivity::class.java).apply {

            putExtra("className", clazz.name)
            if (args != null) putExtra("args", args)
        }
    startActivity(intent)
}


inline fun BaseFragment.launchForResult(clazz:Class<out BaseFragment?>,args: FragmentArgs? = null,requestCode:Int){
    val intent =
        Intent(activity, FragmentContainerActivity::class.java).apply {

            putExtra("className", clazz.name)
            if (args != null) putExtra("args", args)
        }
    startActivityForResult(intent,requestCode)
}