package com.dongldh.carrot.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dongldh.carrot.R
import com.dongldh.carrot.util.App
import com.dongldh.carrot.util.MessageUtil
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener {
            if(verifyIdAndPassword()) {
                val auth = FirebaseAuth.getInstance()
                val email = "${input_id.text}@carrot.com"
                val password = "${input_password.text}"

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        MessageUtil.toastShort("로그인 성공")
                        App.pref.uid = task.result?.user?.uid
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        showSuggestSignUpDialog()
                    }
                }
            }

        }
    }

    private fun verifyIdAndPassword(): Boolean {
        var correctFormat = false
        when {
            input_id.text.isNullOrEmpty() -> MessageUtil.toastShort("아이디를 입력해주세요")
            input_id.text.length > 20 -> MessageUtil.toastShort("아이디가 너무 깁니다")
            input_password.text.isNullOrEmpty() -> MessageUtil.toastShort("비밀번호를 입력해주세요")
            else -> correctFormat = true
        }
        return correctFormat
    }


    private fun showSuggestSignUpDialog() {
        AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog)
            .apply {
                setMessage("등록되지 않은 회원 정보입니다.\n입력하신 정보로 회원 가입을 하시겠습니까?")
                setTitle("로그인 실패")
                setPositiveButton("예") { dialog, which ->  }
                setNegativeButton("아니오") { dialog, which ->  }
                setCancelable(false)
                show()
            }
    }
}