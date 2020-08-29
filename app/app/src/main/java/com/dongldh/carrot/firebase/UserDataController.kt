package com.dongldh.carrot.firebase

import android.content.Intent
import com.dongldh.carrot.data.User
import com.dongldh.carrot.ui.MainActivity
import com.dongldh.carrot.util.App
import com.google.firebase.firestore.FirebaseFirestore

class UserDataController(val db: FirebaseFirestore) {

    /***
     *  새로 회원가입하는 User의 정보를 Firestore에 저장합니다.
     */
    fun addUserInfo(user: User) {
        db.collection("users")
            .add(user)
            .addOnSuccessListener {
                val intent = Intent(App.applicationContext(), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                App.applicationContext().startActivity(intent)
            }
            .addOnFailureListener{ }
    }
}