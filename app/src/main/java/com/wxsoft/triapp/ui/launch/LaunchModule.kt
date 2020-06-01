//package com.wxsoft.triapp.ui.launch
//
//import androidx.lifecycle.ViewModel
//import com.warriorsfly.core.di.ChildFragmentScoped
//import com.warriorsfly.core.di.ViewModelKey
//import dagger.Binds
//import dagger.Module
//import dagger.android.ContributesAndroidInjector
//import dagger.multibindings.IntoMap
//
//@Module
//internal abstract class LaunchModule {
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(LauncherViewModel::class)
//    abstract fun bindLauncherViewModel(viewModel: LauncherViewModel): ViewModel
//
//    @ChildFragmentScoped
//    @ContributesAndroidInjector
//    internal abstract fun contributeUpgradeDialog(): UpgradeDialog
//
//}