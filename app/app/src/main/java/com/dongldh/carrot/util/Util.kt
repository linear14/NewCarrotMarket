package com.dongldh.carrot.util

import android.widget.Toast
import com.dongldh.carrot.R

object Util {

    fun toastShort(msg: String) {
        Toast.makeText(App.applicationContext(), msg, Toast.LENGTH_SHORT).show()
    }

    fun toastLong(msg: String) {
        Toast.makeText(App.applicationContext(), msg, Toast.LENGTH_LONG).show()
    }

    fun showErrorToast() {
        toastShort(App.applicationContext().resources.getString(R.string.exceptional_error))
    }
}