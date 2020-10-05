package com.dongldh.carrot.util

object SharedUtil {
    fun attachUidToSharedPreference(uid: String?) {
        App.pref.uid = uid
    }

    fun detachUidFromSharedPreference() {
        App.pref.uid = UID_DETACHED
    }

    fun attachRegion(regionList: ArrayList<Pair<Long, String>>, selectedRegion: Pair<Long, String>) {
        App.pref.regionList = regionList
        App.pref.regionSelected = selectedRegion
    }

    fun detachRegion() {
        App.pref.regionList = arrayListOf()
        App.pref.regionSelected = Pair(NO_REGION_DATA, "")
    }
}