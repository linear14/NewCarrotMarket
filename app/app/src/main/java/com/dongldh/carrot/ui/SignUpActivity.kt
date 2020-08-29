package com.dongldh.carrot.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dongldh.carrot.R
import com.dongldh.carrot.data.User
import com.dongldh.carrot.firebase.UserDataController
import com.dongldh.carrot.util.Util
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore
    lateinit var userDataController: UserDataController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        userDataController = UserDataController(db)

        btn_sign_up.setOnClickListener {
            val email = "${intent.getStringExtra("ACCOUNT_ID")}@carrot.com"
            val password = intent.getStringExtra("ACCOUNT_PASSWORD")?:throw Exception()
            createUserFirebase(email, password)
        }
    }

    private fun createUserFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if(task.isSuccessful) {
                val uid = task.result?.user?.uid!!
                Util.attachUid(uid)
                userDataController.addUserInfo(makeNewUser(uid))
            } else {
                Util.toastShort("회원가입에 실패하였습니다.(Firebase)")
            }
        }
    }

    private fun makeNewUser(uid: String): User {
        return User(
            uid = uid,
            nickname = input_nickname.text.toString(),
            profileUrl = "nothing"
        )
    }
}