package com.dongldh.carrot.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dongldh.carrot.R
import com.dongldh.carrot.util.App
import com.dongldh.carrot.util.Util
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        auth = FirebaseAuth.getInstance()

        btn_sign_in.setOnClickListener {
            if(verifyIdAndPassword()) {
                val email = "${input_id.text}@carrot.com"
                val password = "${input_password.text}"

                trySignIn(email, password)
            }
        }
    }

    private fun trySignIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Util.toastShort("로그인 성공")
                Util.attachUid(task.result?.user?.uid)
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                val errorCode = (task.exception as FirebaseAuthException).errorCode
                when(errorCode) {
                    "ERROR_WRONG_PASSWORD" -> Util.toastShort("비밀번호가 틀렸습니다.")
                    "ERROR_USER_NOT_FOUND" -> showSuggestSignUpDialog()
                }
            }
        }
    }

    private fun verifyIdAndPassword(): Boolean {
        var correctFormat = false
        when {
            input_id.text.isNullOrEmpty() -> Util.toastShort("아이디를 입력해주세요")
            input_id.text.length > 20 -> Util.toastShort("아이디가 너무 깁니다")
            input_password.text.isNullOrEmpty() -> Util.toastShort("비밀번호를 입력해주세요")
            input_password.text.length < 8 -> Util.toastShort("비밀번호가 너무 짧습니다.")
            else -> correctFormat = true
        }
        return correctFormat
    }

    private fun showSuggestSignUpDialog() {
        AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog)
            .apply {
                setMessage("등록되지 않은 회원 정보입니다.\n입력하신 정보로 회원 가입을 하시겠습니까?")
                setTitle("로그인 실패")
                setPositiveButton("예") { dialog, which ->  moveSignUpPageWithAccountInfo() }
                setNegativeButton("아니오") { dialog, which ->  }
                setCancelable(false)
                show()
            }
    }

    private fun moveSignUpPageWithAccountInfo() {
        val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            .apply {
                putExtra("ACCOUNT_ID", input_id.text.toString())
                putExtra("ACCOUNT_PASSWORD", input_password.text.toString())
            }
        startActivity(intent)
    }
}