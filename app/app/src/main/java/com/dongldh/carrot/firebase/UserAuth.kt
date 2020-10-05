package com.dongldh.carrot.firebase

import android.app.Activity
import com.dongldh.carrot.R
import com.dongldh.carrot.data.User
import com.dongldh.carrot.data.UserCreateAccountRequest
import com.dongldh.carrot.util.SharedUtil
import com.dongldh.carrot.util.Util
import com.google.firebase.auth.FirebaseAuth

class UserAuth(private val activity: Activity) {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun createUserFirebaseAuth(userAccountInfo: UserCreateAccountRequest) {
        auth.createUserWithEmailAndPassword(userAccountInfo.email, userAccountInfo.password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val uid = task.result?.user?.uid!!
                    SharedUtil.attachUidToSharedPreference(uid)
                    UserFirestoreManager.addUserInfo(makeNewUser(uid, userAccountInfo))
                } else {
                    Util.toastShort(activity.resources.getString(R.string.firebase_auth_create_user_error))
                }
            }
    }

    private fun makeNewUser(uid: String, userAccountInfo: UserCreateAccountRequest): User {
        val regionIdList = mutableListOf<Long>().apply { add(userAccountInfo.regionId) }
        val regionStringList = mutableListOf<String>().apply { add(userAccountInfo.regionString) }

        return User(
            uid = uid,
            nickname = userAccountInfo.nickName,
            profileUrl = userAccountInfo.profileImageUrl,
            regionIdAll = regionIdList,
            regionStringAll = regionStringList,
            regionIdSelected = userAccountInfo.regionId,
            regionStringSelected = userAccountInfo.regionString
        )
    }
}