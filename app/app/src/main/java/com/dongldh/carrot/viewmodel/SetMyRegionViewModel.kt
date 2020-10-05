package com.dongldh.carrot.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dongldh.carrot.util.App

class SetMyRegionViewModel: ViewModel() {
    val regionList: MutableLiveData<List<Pair<Long, String>>> = MutableLiveData(App.pref.regionList)
    val regionSelectedPair: MutableLiveData<Pair<Long,String>> = MutableLiveData(App.pref.regionSelected)

    fun getSelectedPosition(): Int {
        var selectedPosition = -1
        for((index, region) in regionList.value!!.withIndex()) {
            if(regionSelectedPair.value?.first == region.first) {
                selectedPosition = index
            }
        }
        return selectedPosition
    }
}