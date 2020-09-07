package com.dongldh.carrot.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dongldh.carrot.R
import com.dongldh.carrot.firebase.UserFirestoreManager
import com.dongldh.carrot.util.Util
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore
    lateinit var userDataController: UserFirestoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        userDataController = UserFirestoreManager(db)

        action_next.setOnClickListener {
            if(input_nickname.text.toString().length < 2) {
                Util.toastShort("아이디가 너무 짧습니다.")
            } else if(input_nickname.text.toString().length > 8) {
                Util.toastShort("아이디가 너무 깁니다.")
            } else {
                val intent = Intent(this, RegionListActivity::class.java)
                intent.putExtra("ACCOUNT_ID", this.intent.getStringExtra("ACCOUNT_ID"))
                intent.putExtra("ACCOUNT_PASSWORD", this.intent.getStringExtra("ACCOUNT_PASSWORD"))
                intent.putExtra("ACCOUNT_NICKNAME", input_nickname.text.toString())
                intent.putExtra("ACCOUNT_PROFILE_IMAGE_URL", "https://아직없다")
                startActivity(intent)
            }
        }

        input_nickname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().length < 2) {
                    layout_nickname.error = resources.getString(R.string.nickname_min)
                } else if(s.toString().length > 8) {
                    layout_nickname.error = resources.getString(R.string.nickname_max)
                } else {
                    layout_nickname.error = null
                }
            }

        })

        input_nickname.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus) {
                layout_nickname.hint = resources.getString(R.string.hint_write_nickname_label)
            } else {
                layout_nickname.hint = resources.getString(R.string.hint_write_nickname)
            }
        }
    }
}