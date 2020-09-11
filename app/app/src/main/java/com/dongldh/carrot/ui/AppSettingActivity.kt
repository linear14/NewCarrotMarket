package com.dongldh.carrot.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dongldh.carrot.R
import com.dongldh.carrot.util.Util
import kotlinx.android.synthetic.main.activity_app_setting.*

class AppSettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_setting)

        text_sign_out.setOnClickListener {
            Util.detachUidFromSharedPreference()
            val intent = Intent(this, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}