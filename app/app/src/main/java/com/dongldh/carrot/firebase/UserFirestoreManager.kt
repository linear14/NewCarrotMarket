package com.dongldh.carrot.firebase

import android.content.Intent
import com.dongldh.carrot.R
import com.dongldh.carrot.data.User
import com.dongldh.carrot.ui.MainActivity
import com.dongldh.carrot.util.App
import com.dongldh.carrot.util.COLLECTION_USERS
import com.dongldh.carrot.util.Util
import com.google.firebase.firestore.FirebaseFirestore

class UserFirestoreManager(val db: FirebaseFirestore) {
    /***
     *  새로 회원가입하는 User의 정보를 Firestore에 저장합니다.
     */
    fun addUserInfo(user: User) {
        db.collection(COLLECTION_USERS)
            .document(user.uid!!)
            .set(user)
            .addOnSuccessListener {
                val intent = Intent(App.applicationContext(), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                App.applicationContext().startActivity(intent)
                Util.toastShort(App.applicationContext().resources.getString(R.string.firebase_create_account_ok))
            }
            .addOnFailureListener{
                Util.toastShort(App.applicationContext().resources.getString(R.string.firebase_auth_create_user_error))
            }
    }
}