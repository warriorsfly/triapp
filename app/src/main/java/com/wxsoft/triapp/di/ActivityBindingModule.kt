package com.wxsoft.triapp.di

import com.wxsoft.triapp.ui.login.LoginActivity
import com.warriorsfly.core.di.ActivityScoped
import com.wxsoft.triapp.ui.login.LoginModule
import com.wxsoft.triapp.ui.main.MainActivity
import com.wxsoft.triapp.ui.main.HomeModule
import com.wxsoft.triapp.ui.patient.PatientActivity
import com.wxsoft.triapp.ui.patient.PatientModule
import com.wxsoft.triapp.ui.rating.RatingModule
import com.wxsoft.triapp.ui.rating.RatingSubjectActivity
import com.wxsoft.triapp.ui.widgets.FragmentContainerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * We want Dagger.Android to create a Subcomponent which has a parent Component of whichever module
 * ActivityBindingModule is on, in our case that will be [AppComponent]. You never
 * need to tell [AppComponent] that it is going to have getAll these subcomponents
 * nor do you need to tell these subcomponents that [AppComponent] exists.
 * We are also telling Dagger.Android that this generated SubComponent needs to include the
 * specified modules and be aware of a scope annotation [@ActivityScoped].
 * When Dagger.Android annotation processor runs it will create 2 subcomponents for us.
 */
@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [LoginModule::class])
    internal abstract fun loginActivity(): LoginActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [HomeModule::class])
    internal abstract fun mainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [PatientModule::class])
    internal abstract fun patientActivity(): PatientActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [PatientModule::class])
    internal abstract fun containActivity(): FragmentContainerActivity


    @ActivityScoped
    @ContributesAndroidInjector(modules = [RatingModule::class])
    internal abstract fun ratingSubjectActivity(): RatingSubjectActivity
}