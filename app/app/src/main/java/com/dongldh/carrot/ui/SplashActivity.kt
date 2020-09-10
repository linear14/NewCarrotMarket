package com.dongldh.carrot.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dongldh.carrot.R
import com.dongldh.carrot.util.App
import com.dongldh.carrot.util.UID_DETACHED

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread.sleep(1500)

        val userLoginUid = App.pref.uid

        if(userLoginUid == UID_DETACHED) {
            startActivity(Intent(this, SignInActivity::class.java))
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
        finish()
    }
}