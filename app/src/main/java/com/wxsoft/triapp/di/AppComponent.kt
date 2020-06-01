package com.wxsoft.triapp.di

import com.wxsoft.triapp.App
import com.warriorsfly.core.di.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBindingModule::class,
        NetWorkModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {


    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: App): AppComponent
    }
//    @Component.Builder
//    abstract class Builder : AndroidInjector.Builder<App>()
}