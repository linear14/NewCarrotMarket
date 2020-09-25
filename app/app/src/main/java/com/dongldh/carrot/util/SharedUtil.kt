package com.dongldh.carrot.util

import com.dongldh.carrot.data.AppDatabase

object SharedUtil {
    fun attachUidToSharedPreference(uid: String?) {
        App.pref.uid = uid
    }

    fun detachUidFromSharedPreference() {
        App.pref.uid = UID_DETACHED
    }

    suspend fun attachRegion(regionIdAll: List<Long>, regionIdSelected: Long) {
        val sharedRegionList = ArrayList<Pair<Long, String>>()

        val myRegionList = AppDatabase.getInstance(App.applicationContext()).regionDao().selectRegionById(regionIdAll)
        for(region in myRegionList) {
            sharedRegionList.add(Pair(region.id, region.name))
        }
        App.pref.regionList = sharedRegionList

        val selectedRegion = AppDatabase.getInstance(App.applicationContext()).regionDao().selectRegionById(listOf(regionIdSelected))[0]
        App.pref.regionSelected = selectedRegion.name
    }

    fun detachRegion() {
        App.pref.regionList = arrayListOf()
        App.pref.regionSelected = REGION_SELECTED_DETACHED
    }
}