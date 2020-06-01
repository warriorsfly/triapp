package com.wxsoft.triapp.ui.main

import androidx.lifecycle.ViewModel
import com.warriorsfly.core.di.FragmentScoped
import com.warriorsfly.core.di.ViewModelKey
import com.wxsoft.triapp.ui.main.fragment.*
import com.wxsoft.triapp.ui.rating.RatingFragment
import com.wxsoft.triapp.ui.rating.RatingViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class HomeModule {
    @Binds
    @IntoMap
    @ViewModelKey(PatientWithDetailModel::class)
    abstract fun bindPatientWithDetailModel(viewModel: PatientWithDetailModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserProfileViewModel::class)
    abstract fun bindUserProfileViewModel(viewModel: UserProfileViewModel): ViewModel

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun contributePatientListFragment(): PatientListFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun contributeStatisticFragment(): StatisticFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun contributeUserProfileFragment(): UserProfileFragment

}