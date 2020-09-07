package com.dongldh.carrot.util

import android.content.Context

class SharedPreference(context: Context) {
    val pref = context.getSharedPreferences("carrot_pref", 0)

    var uid: String?
        get() = pref.getString("CURRENT_UID", UID_DETACHED)
        set(value) = pref.edit().putString("CURRENT_UID", value).apply()

    // 계정 생성은 성공했지만, DB에 사용자 정보가 들어가지 않았을 경우 email 주소를 담는다.
    // (나중에 계정 삭제를 위해서임)
    var failedFirestoreSavedEmailAddress: String?
        get() = pref.getString("FAILED_SAVED_EMAIL", null)
        set(value) = pref.edit().putString("FAILED_SAVED_EMAIL", value).apply()
}