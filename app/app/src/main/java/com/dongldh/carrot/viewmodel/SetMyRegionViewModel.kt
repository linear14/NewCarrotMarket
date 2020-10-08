package com.dongldh.carrot.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dongldh.carrot.R
import com.dongldh.carrot.`interface`.OnFinishNetworkingListener
import com.dongldh.carrot.firebase.UserFirestoreManager
import com.dongldh.carrot.util.App
import com.dongldh.carrot.util.UID_DETACHED
import com.dongldh.carrot.util.Util

class SetMyRegionViewModel: ViewModel() {
    val regionList: MutableLiveData<List<Pair<Long, String>>> = MutableLiveData(App.pref.regionList)
    val regionSelectedPair: MutableLiveData<Pair<Long, String>> = MutableLiveData(App.pref.regionSelected)

    fun getSelectedPosition(): Int {
        var selectedPosition = -1
        for((index, region) in regionList.value!!.withIndex()) {
            if(regionSelectedPair.value?.first == region.first) {
                selectedPosition = index
            }
        }
        return selectedPosition
    }

    fun initLiveData() {
        regionList.value = App.pref.regionList
        regionSelectedPair.value = App.pref.regionSelected
    }

    fun updateSelectedRegion(selectedPair: Pair<Long, String>) {
        UserFirestoreManager.updateSelectedRegion(App.pref.uid ?: UID_DETACHED, selectedPair, object: OnFinishNetworkingListener {
            override fun onSuccess() {
                regionSelectedPair.value = selectedPair
            }

            override fun onFailure() {
                Util.toastShort(App.applicationContext().resources.getString(R.string.fail_update_region))
            }
        })
    }

    fun deleteRegion(remainRegion: Pair<Long, String>) {
        UserFirestoreManager.remainOnlyOneRegion(App.pref.uid ?: UID_DETACHED, remainRegion, object: OnFinishNetworkingListener {
            override fun onSuccess() {
                initLiveData()
            }

            override fun onFailure() {
                Util.toastShort(App.applicationContext().resources.getString(R.string.fail_delete_region))
            }

        })
    }
}