package com.dongldh.carrot.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dongldh.carrot.data.AppDatabase
import com.dongldh.carrot.data.Region
import com.dongldh.carrot.util.STATE_INIT

class RegionListViewModel(
    application: Application
): AndroidViewModel(application) {

    /*** filterText : 검색어 ***/
    var filterText = MutableLiveData<String>(STATE_INIT)
    private var allRegionsLiveData: LiveData<PagedList<Region>>? = null

    private val config = PagedList.Config.Builder().setEnablePlaceholders(false).setPageSize(16).build()

    init {
        setRegionsInitOrWhenFilterTextChanged()
    }

    fun getRegionsLiveData() = allRegionsLiveData

    private fun setRegionsInitOrWhenFilterTextChanged() {
        allRegionsLiveData = Transformations.switchMap(filterText) { str -> getRegionsInitOrWhenFilterTextChanged(str) }
    }

    private fun getRegionsInitOrWhenFilterTextChanged(str: String): LiveData<PagedList<Region>> =
        if(str == STATE_INIT) getRegionsAll() else getRegionsFiltered(str)


    private fun getRegionsAll(): LiveData<PagedList<Region>> {
        val factory: DataSource.Factory<Int, Region> =
            AppDatabase.getInstance(getApplication()).regionDao().selectAllRegions()
        return LivePagedListBuilder(factory, config).build()
    }

    private fun getRegionsFiltered(str: String): LiveData<PagedList<Region>> {
        val factory: DataSource.Factory<Int, Region> =
            AppDatabase.getInstance(getApplication()).regionDao().selectRegionsByString("%${str}%")
        return LivePagedListBuilder(factory, config).build()
    }

}