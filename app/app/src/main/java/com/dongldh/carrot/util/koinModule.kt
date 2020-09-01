package com.dongldh.carrot.util

import com.dongldh.carrot.data.AppDatabase
import com.dongldh.carrot.data.RegionRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single { AppDatabase.getInstance(androidApplication()) }
    single(createdAtStart = false) { get<AppDatabase>().regionDao() }
    single { RegionRepository(get()) }
}