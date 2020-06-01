package com.wxsoft.triapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import com.google.gson.Gson
import com.warriorsfly.core.result.Event
import com.wxsoft.triapp.App
import com.wxsoft.triapp.data.QueryModel
import com.wxsoft.triapp.data.entity.JwtRequest
import com.wxsoft.triapp.data.entity.ResponseO
import com.wxsoft.triapp.data.prefs.SharedPreferenceStorage
import com.wxsoft.triapp.data.remote.DictionaryApi
import com.wxsoft.triapp.data.remote.IdentityApi
import com.wxsoft.triapp.ui.BaseViewModel
import dagger.Lazy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel  @Inject constructor (private val identityApi: IdentityApi,
                                           private val dictionaryApi: Lazy<DictionaryApi>,
                     override val sharedPreferenceStorage: SharedPreferenceStorage,
                     override val gon: Gson
): BaseViewModel(sharedPreferenceStorage,gon) {

    val tokenData:LiveData<String>
    private val loadTokenResult= MediatorLiveData<ResponseO<String>>()

    init {
        tokenData=loadTokenResult.map {
            it.Data?:""
        }
    }
    fun login(username:String, password:String){
        val req=JwtRequest(username,password)
        disposable.add(
            identityApi.getJwt(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    if(it.Type==500){
                        messageAction.value= Event(it.Content)
                    }else {
                        sharedPreferenceStorage.token = it.Data
                        loadTokenResult.value = it

                        dictionaryApi.get().getProfile()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ user ->
                                account = user
                            }, ::error)
                        dictionaryApi.get().read(QueryModel())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                App.dictionaries.addAll(it.Rows)
                            }, ::error)
                    }
                },::error)
        )

    }
}