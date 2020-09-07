package com.dongldh.carrot.firebase

import android.content.Intent
import com.dongldh.carrot.data.User
import com.dongldh.carrot.ui.MainActivity
import com.dongldh.carrot.util.App
import com.dongldh.carrot.util.Util
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up.*

class UserFirestoreManager(val db: FirebaseFirestore) {
    /***
     *  새로 회원가입하는 User의 정보를 Firestore에 저장합니다.
     */
    fun addUserInfo(user: User) {
        db.collection("users")
            .document(user.uid!!)
            .set(user)
            .addOnSuccessListener {
                val intent = Intent(App.applicationContext(), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                App.applicationContext().startActivity(intent)
                Util.toastShort("회원가입 성공")
            }
            .addOnFailureListener{ }
    }
}