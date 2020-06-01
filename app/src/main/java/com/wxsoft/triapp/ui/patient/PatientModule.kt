package com.wxsoft.triapp.ui.patient


import androidx.lifecycle.ViewModel
import com.warriorsfly.core.di.ChildFragmentScoped
import com.warriorsfly.core.di.FragmentScoped
import com.warriorsfly.core.di.ViewModelKey
import com.wxsoft.triapp.ui.rating.RatingFragment
import com.wxsoft.triapp.ui.rating.RatingViewModel
import com.wxsoft.triapp.ui.rating.RatingsSheetFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class PatientModule {
    @Binds
    @IntoMap
    @ViewModelKey(PatientRescueModel::class)
    abstract fun bindPatientRescueModel(viewModel: PatientRescueModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PatientRescueEditModel::class)
    abstract fun bindPatientRescueEditModel(viewModel: PatientRescueEditModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LisModel::class)
    abstract fun bindLisModel(viewModel: LisModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RatingViewModel::class)
    abstract fun bindRatingViewModel(viewModel: RatingViewModel): ViewModel

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun contributePatientRescueListFragment(): PatientRescueListFragment


    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun contributePatientRescueFragment(): PatientRescueFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun contributePatientDetailFragment(): PatientDetailFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun contributeLisFragment(): LisFragment


    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun contributeRatingFragment(): RatingFragment


    @ChildFragmentScoped
    @ContributesAndroidInjector
    abstract fun contributeRatingsSheetFragment(): RatingsSheetFragment
}