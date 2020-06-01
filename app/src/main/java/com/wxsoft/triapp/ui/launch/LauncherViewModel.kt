package com.wxsoft.triapp.ui.launch

import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import com.google.gson.Gson
import com.wxsoft.triapp.data.prefs.SharedPreferenceStorage
import com.wxsoft.triapp.ui.BaseViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LauncherViewModel @Inject constructor(
//    private val api: VersionApi,
    override val sharedPreferenceStorage: SharedPreferenceStorage,
    override val gon: Gson
) : BaseViewModel(sharedPreferenceStorage,gon) {

    val processPercent=ObservableInt().apply {
        set(0)
    }
    val processTotal=ObservableInt().apply {
        set(0)
    }


    val update = MediatorLiveData<Boolean>()


    var local:Long=0
        set(value){
            field=value
            loadVersion()
        }

    val version:LiveData<Int>
    private val loadVersionResult=MediatorLiveData<Int>()

    init {
        version=loadVersionResult.map {
            it
        }
    }


    private fun loadVersion() {
//        disposable.add(api.check(local)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(::checkUpdate,::error))
    }

    private fun checkUpdate(response: Int){

    }

    fun startUpdate(){
        update.value=true
    }

}