package com.dongldh.carrot.util

import com.dongldh.carrot.data.AppDatabase
import com.dongldh.carrot.data.RegionRepository
import com.dongldh.carrot.viewmodel.RegionListViewModel
import com.dongldh.carrot.viewmodel.RegionViewModel
import com.dongldh.carrot.viewmodel.SetMyRegionViewModel
import com.dongldh.carrot.viewmodel.UserViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { AppDatabase.getInstance(androidApplication()) }
    single(createdAtStart = false) { get<AppDatabase>().regionDao() }
    single { RegionRepository(get()) }

    viewModel { RegionListViewModel(get()) }
    viewModel { RegionViewModel(get()) }
    viewModel { (uid: String) -> UserViewModel(uid) }
    viewModel { SetMyRegionViewModel() }
}