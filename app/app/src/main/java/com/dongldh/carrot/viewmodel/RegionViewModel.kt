package com.dongldh.carrot.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dongldh.carrot.data.Region
import com.dongldh.carrot.data.RegionRepository
import com.dongldh.carrot.data.User
import com.dongldh.carrot.firebase.UserFirestoreManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegionViewModel(
    val regionRepository: RegionRepository
) : ViewModel() {
    val regionList: MutableLiveData<List<Region>> = MutableLiveData()

    fun getRegionListById(regionId: List<Long>) {
        GlobalScope.launch { regionList.postValue(regionRepository.selectRegionById(regionId)) }
    }
}