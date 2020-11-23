package com.dongldh.carrot.util

import com.dongldh.carrot.data.User

object SharedUtil {
    fun attachUidToSharedPreference(uid: String?) {
        App.pref.uid = uid
    }

    fun detachUidFromSharedPreference() {
        App.pref.uid = UID_DETACHED
    }

    fun attachRegionToSharedPreference(user: User) {
        val regionPairList = Util.getRegionPairList(user.regionIdAll, user.regionStringAll)
        val selectedRegionPair = Util.getRegionSelectedPair(user.regionIdSelected!!, user.regionStringSelected!!)

        App.pref.regionPairList = regionPairList
        App.pref.selectedRegionPair = selectedRegionPair
    }

    fun detachRegionFromSharedPreference() {
        App.pref.regionPairList = arrayListOf()
        App.pref.selectedRegionPair = Pair(NO_REGION_DATA, "")
    }
}