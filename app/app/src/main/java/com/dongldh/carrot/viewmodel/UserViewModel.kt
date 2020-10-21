package com.dongldh.carrot.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dongldh.carrot.data.User
import com.dongldh.carrot.firebase.UserFirestore

class UserViewModel(uid: String) : ViewModel() {
    val currentUser: MutableLiveData<User> = MutableLiveData()

    init {
        setCurrentUserByUid(uid, currentUser)
    }

    private fun setCurrentUserByUid(uid: String, currentUser: MutableLiveData<User>) {
        UserFirestore.getUserInfoLiveDataByUid(uid, currentUser)
    }
}