package com.wxsoft.triapp.ui.main.fragment

import com.google.gson.Gson
import com.wxsoft.triapp.data.prefs.SharedPreferenceStorage
import com.wxsoft.triapp.data.remote.IdentityApi
import com.wxsoft.triapp.ui.BaseViewModel
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(private val accountApi:IdentityApi ,
                                               override val sharedPreferenceStorage: SharedPreferenceStorage,
                                               override val gon: Gson
):  BaseViewModel(sharedPreferenceStorage,gon) {

}
