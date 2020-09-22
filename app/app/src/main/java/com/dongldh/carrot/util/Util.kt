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

    fun toastExceptionalError() {
        toastShort(App.applicationContext().resources.getString(R.string.exceptional_error))
    }

    fun attachUidToSharedPreference(uid: String?) {
        App.pref.uid = uid
    }

    fun detachUidFromSharedPreference() {
        App.pref.uid = UID_DETACHED
    }
}