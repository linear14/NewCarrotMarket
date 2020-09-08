package com.dongldh.carrot.firebase

import android.app.Activity
import com.dongldh.carrot.R
import com.dongldh.carrot.data.User
import com.dongldh.carrot.data.UserCreateAccountRequest
import com.dongldh.carrot.util.Util
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserAuth(private val activity: Activity) {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun createUserFirebaseAuth(userAccountInfo: UserCreateAccountRequest) {
        auth.createUserWithEmailAndPassword(userAccountInfo.email, userAccountInfo.password).addOnCompleteListener(activity) { task ->
            if(task.isSuccessful) {
                val uid = task.result?.user?.uid!!
                Util.attachUidToSharedPreference(uid)
                UserFirestoreManager(db).apply { addUserInfo(makeNewUser(uid, userAccountInfo)) }
            } else {
                Util.toastShort(activity.resources.getString(R.string.firebase_auth_create_user_error))
            }
        }
    }

    private fun makeNewUser(uid: String, userAccountInfo: UserCreateAccountRequest): User {
        val regionList = mutableListOf<String>().apply { add(userAccountInfo.region) }

        return User(
                uid = uid,
                nickname = userAccountInfo.nickName,
                profileUrl = userAccountInfo.profileImageUrl,
                region = regionList
            )
    }
}