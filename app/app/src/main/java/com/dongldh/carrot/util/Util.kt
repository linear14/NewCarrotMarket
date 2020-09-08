package com.dongldh.carrot.util

import android.widget.Toast

object Util {

    fun toastShort(msg: String) {
        Toast.makeText(App.applicationContext(), msg, Toast.LENGTH_SHORT).show()
    }

    fun toastLong(msg: String) {
        Toast.makeText(App.applicationContext(), msg, Toast.LENGTH_LONG).show()
    }

    fun attachUidToSharedPreference(uid: String?) {
        App.pref.uid = uid
    }

    fun detachUidFromSharedPreference() {
        App.pref.uid = UID_DETACHED
    }
}