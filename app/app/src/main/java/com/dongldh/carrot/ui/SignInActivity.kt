package com.dongldh.carrot.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dongldh.carrot.R
import com.dongldh.carrot.`interface`.OnFinishUserNetworkingListener
import com.dongldh.carrot.data.User
import com.dongldh.carrot.firebase.UserFirestore
import com.dongldh.carrot.util.*
import com.dongldh.carrot.util.Util.setUserRegionInfoToSharedPreference
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        btn_sign_in.setOnClickListener {
            if(verifyProperFormatIdAndPassword()) { makeEmailFormatAndSignIn() }
        }
    }

    private fun verifyProperFormatIdAndPassword(): Boolean {
        var correctFormat = false
        when {
            input_id.text.isNullOrEmpty() -> Util.toastShort(resources.getString(R.string.input_id_blank))
            input_id.text.length > 20 -> Util.toastShort(resources.getString(R.string.input_id_too_long))
            input_password.text.isNullOrEmpty() -> Util.toastShort(resources.getString(R.string.input_password_blank))
            input_password.text.length < 8 -> Util.toastShort(resources.getString(R.string.input_password_too_short))
            else -> correctFormat = true
        }
        return correctFormat
    }

    private fun makeEmailFormatAndSignIn() {
        val email = "${input_id.text}@carrot.com"
        val password = "${input_password.text}"

        trySignIn(email, password)
    }

    private fun trySignIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task -> trySignInResult(task) }
    }

    private fun trySignInResult(task: Task<AuthResult>) {
        if (task.isSuccessful) {
            Util.toastShort(resources.getString(R.string.firebase_sign_in_success))
            attachUidToSharedPreferenceAndGoToMainActivity(task)
        } else {
            val errorCode = (task.exception as FirebaseAuthException).errorCode
            actionAccordingToError(errorCode)
        }
    }

    private fun attachUidToSharedPreferenceAndGoToMainActivity(task: Task<AuthResult>) {
        SharedUtil.attachUidToSharedPreference(task.result?.user?.uid)
        getUserInfoFromFireStoreAndAttachAccountInfoToSharedPreference()
    }

    private fun actionAccordingToError(errorCode: String) {
        when(errorCode) {
            ERROR_WRONG_PASSWORD -> Util.toastShort(resources.getString(R.string.firebase_wrong_password))
            ERROR_USER_NOT_FOUND -> showSuggestSignUpDialog()
        }
    }

    private fun showSuggestSignUpDialog() {
        AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog)
            .apply {
                setMessage(resources.getString(R.string.dialog_firebase_not_exist_account))
                setTitle(resources.getString(R.string.firebase_sign_in_failed))
                setPositiveButton(resources.getString(R.string.yes)) { dialog, which ->  moveSignUpPageWithAccountInfo() }
                setNegativeButton(resources.getString(R.string.no)) { dialog, which ->  }
                setCancelable(false)
                show()
            }
    }

    private fun moveSignUpPageWithAccountInfo() {
        val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            .apply {
                putExtra(ACCOUNT_ID, input_id.text.toString())
                putExtra(ACCOUNT_PASSWORD, input_password.text.toString())
            }
        startActivity(intent)
    }

    private fun getUserInfoFromFireStoreAndAttachAccountInfoToSharedPreference() {
        App.pref.uid?.let {
            UserFirestore.getUserInfoLiveDataByUid(it, object: OnFinishUserNetworkingListener {
                override fun onSuccess(user: User?) {
                    setUserRegionInfoToSharedPreference(user!!)
                    
                    val intent = Intent(App.applicationContext(), MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    App.applicationContext().startActivity(intent)
                }
                override fun onFailure() {
                    Util.toastShort("회원정보를 불러오는데 실패하였습니다")
                }
            })
        }?:Util.showErrorToast()
    }
}