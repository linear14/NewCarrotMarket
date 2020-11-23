package com.dongldh.carrot.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dongldh.carrot.R
import com.dongldh.carrot.`interface`.OnFinishUserNetworkingListener
import com.dongldh.carrot.data.User
import com.dongldh.carrot.firebase.UserFirestore
import com.dongldh.carrot.util.App
import com.dongldh.carrot.util.UID_DETACHED
import com.dongldh.carrot.util.Util

class SetMyRegionViewModel: ViewModel() {
    val regionPairList: MutableLiveData<List<Pair<Long, String>>> = MutableLiveData(App.pref.regionPairList)
    val selectedRegionPair: MutableLiveData<Pair<Long, String>> = MutableLiveData(App.pref.selectedRegionPair)

    fun getSelectedPosition(): Int {
        var selectedPosition = -1
        for((index, region) in regionPairList.value!!.withIndex()) {
            if(selectedRegionPair.value?.first == region.first) {
                selectedPosition = index
            }
        }
        return selectedPosition
    }

    fun initLiveData() {
        regionPairList.value = App.pref.regionPairList
        selectedRegionPair.value = App.pref.selectedRegionPair
    }

    fun updateSelectedRegion(selectedPair: Pair<Long, String>) {
        UserFirestore.updateSelectedRegion(App.pref.uid ?: UID_DETACHED, selectedPair, object:
            OnFinishUserNetworkingListener {
            override fun onSuccess(user: User?) {
                selectedRegionPair.value = selectedPair
            }

            override fun onFailure() {
                Util.toastShort(App.applicationContext().resources.getString(R.string.fail_update_region))
            }
        })
    }

    fun deleteRegion(remainRegion: Pair<Long, String>) {
        UserFirestore.remainOnlyOneRegion(App.pref.uid ?: UID_DETACHED, remainRegion, object: OnFinishUserNetworkingListener {
            override fun onSuccess(user: User?) {
                initLiveData()
            }

            override fun onFailure() {
                Util.toastShort(App.applicationContext().resources.getString(R.string.fail_delete_region))
            }

        })
    }
}