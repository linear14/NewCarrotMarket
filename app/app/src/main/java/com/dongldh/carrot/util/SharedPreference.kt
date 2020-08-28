package com.dongldh.carrot.util

import android.content.Context

class SharedPreference(context: Context) {
    val pref = context.getSharedPreferences("carrot_pref", 0)

    var uid: String?
        get() = pref.getString("CURRENT_UID", UID_DETACHED)
        set(value) = pref.edit().putString("CURRENT_UID", value).apply()
}