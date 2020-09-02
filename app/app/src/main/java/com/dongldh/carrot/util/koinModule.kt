package com.dongldh.carrot.util

import com.dongldh.carrot.data.AppDatabase
import com.dongldh.carrot.viewmodel.RegionListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { AppDatabase.getInstance(androidApplication()) }
    single(createdAtStart = false) { get<AppDatabase>().regionDao() }

    viewModel{ RegionListViewModel(get()) }
}