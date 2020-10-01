package com.dongldh.carrot.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dongldh.carrot.util.App

class SetMyRegionViewModel: ViewModel() {
    val regionList: MutableLiveData<List<Pair<Long, String>>> = MutableLiveData(App.pref.regionList)
    val regionSelectedString: MutableLiveData<String?> = MutableLiveData(App.pref.regionSelected)

    fun getSelectedPosition(): Int {
        var selectedPosition = -1
        for((index, region) in regionList.value!!.withIndex()) {
            if(regionSelectedString.value == region.second) {
                selectedPosition = index
            }
        }
        return selectedPosition
    }
}