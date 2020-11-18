package com.dongldh.carrot.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dongldh.carrot.`interface`.OnFinishUserNetworkingListener
import com.dongldh.carrot.data.User
import com.dongldh.carrot.firebase.UserFirestore
import com.dongldh.carrot.util.Util

class UserViewModel(uid: String) : ViewModel() {
    val currentUser: MutableLiveData<User> = MutableLiveData()

    init {
        setCurrentUserByUid(uid, currentUser)
    }

    private fun setCurrentUserByUid(uid: String, currentUser: MutableLiveData<User>) {
        UserFirestore.getUserInfoLiveDataByUid(uid, object: OnFinishUserNetworkingListener {
            override fun onSuccess(user: User?) {
                currentUser.value = user
            }
            override fun onFailure() {
                Util.toastShort("회원정보를 불러오는데 실패하였습니다")
            }

        })
    }
}