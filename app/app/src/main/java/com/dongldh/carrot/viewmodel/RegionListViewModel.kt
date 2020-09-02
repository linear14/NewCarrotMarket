package com.dongldh.carrot.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dongldh.carrot.data.AppDatabase
import com.dongldh.carrot.data.Region

class RegionListViewModel(
    application: Application
): AndroidViewModel(application) {
    private var allRegionsLiveData: LiveData<PagedList<Region>>

    init {
        val config = PagedList.Config.Builder().setEnablePlaceholders(false).setPageSize(12).build()

        val factory: DataSource.Factory<Int, Region> =
            AppDatabase.getInstance(getApplication()).regionDao().selectAllRegions()

        val pagedListBuilder: LivePagedListBuilder<Int, Region> =
            LivePagedListBuilder<Int, Region>(factory, config)

        allRegionsLiveData = pagedListBuilder.build()
    }

    fun getRegionsLiveData() = allRegionsLiveData
}