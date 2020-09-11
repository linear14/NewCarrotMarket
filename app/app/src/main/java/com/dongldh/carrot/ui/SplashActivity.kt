package com.dongldh.carrot.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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