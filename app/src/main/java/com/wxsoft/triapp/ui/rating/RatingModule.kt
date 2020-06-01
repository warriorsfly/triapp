package com.wxsoft.triapp.ui.rating

import androidx.lifecycle.ViewModel
import com.warriorsfly.core.di.ChildFragmentScoped
import com.warriorsfly.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class RatingModule {
    @Binds
    @IntoMap
    @ViewModelKey(RatingViewModel::class)
    abstract fun bindRatingViewModel(viewModel: RatingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RatingSubjectViewModel::class)
    abstract fun bindRatingSubjectViewModel(viewModel: RatingSubjectViewModel): ViewModel

}