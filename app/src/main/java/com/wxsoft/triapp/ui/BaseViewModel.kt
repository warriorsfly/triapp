package com.wxsoft.triapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.warriorsfly.core.result.Event
import com.wxsoft.triapp.data.entity.User
import com.wxsoft.triapp.data.prefs.SharedPreferenceStorage
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel constructor(protected open val sharedPreferenceStorage: SharedPreferenceStorage,
                                         protected open val gon:Gson): ViewModel() {

    protected val disposable= CompositeDisposable()

    companion object{
        var account: User?=null
    }

    /**
     * 需要传递出去的toast消息
     */
    val messageAction = MutableLiveData<Event<String>>()
    val mesAction: LiveData<Event<String>>
        get() = messageAction

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }



    /**
     * 共用的错误处理
     */
    protected open fun error(throwable: Throwable) {
        messageAction.value = Event(throwable.message ?: "错误")
    }
}