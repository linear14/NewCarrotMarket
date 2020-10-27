package com.dongldh.carrot.util

import android.content.Context
import com.dongldh.carrot.data.NO_PRICE
import org.json.JSONArray
import org.json.JSONException

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

    // 유저 정보
    var regionList: ArrayList<Pair<Long, String>>
        get() {
            val json = pref.getString("REGION_LIST", null)
            val arrayList = ArrayList<Pair<Long, String>>()
            var tempId: Long = NO_REGION_DATA

            if(json != null) {
                try {
                    val jsonArray = JSONArray(json)
                    for(i in 0 until jsonArray.length()) {
                        if(i % 2 == 0) {
                            // Long
                            val id = jsonArray.optLong(i)
                            tempId = id
                        } else {
                            // String
                            val name = jsonArray.optString(i)
                            arrayList.add(Pair(tempId, name))
                            tempId = NO_REGION_DATA
                        }
                    }
                } catch(e: JSONException) {
                    e.printStackTrace()
                }
            }
            return arrayList
        }

        set(values) {
            val editor = pref.edit()
            val jsonArray = JSONArray()
            for(value in values) {
                jsonArray.put(value.first)
                jsonArray.put(value.second)
            }
            if(values.isNotEmpty()) {
                editor.putString("REGION_LIST", jsonArray.toString())
            } else {
                editor.putString("REGION_LIST", null)
            }
            editor.apply()
        }

    var regionSelected: Pair<Long, String>
        get() {
            val json = pref.getString("REGION_SELECTED", null)
            var pair: Pair<Long, String> = Pair(NO_REGION_DATA, "")

            if(json != null) {
                try {
                    val jsonArray = JSONArray(json)
                    val id = jsonArray.optLong(0)
                    val name = jsonArray.optString(1)
                    pair = Pair(id, name)
                } catch(e: JSONException) {
                    e.printStackTrace()
                }
            }
            return pair
        }

        set(value) {
            val editor = pref.edit()
            val jsonArray = JSONArray().apply {
                put(value.first)
                put(value.second)
            }

            editor.putString("REGION_SELECTED", jsonArray.toString())
            editor.apply()
        }

    // 임시 저장된 글이 존재하는지
    var isSavedState: Boolean?
        get() = pref.getBoolean("IS_SAVED_STATE", false)
        set(value) = pref.edit().putBoolean("IS_SAVED_STATE", value?:false).apply()

    var savedTitle: String?
        get() = pref.getString("SAVED_TITLE", "")
        set(value) = pref.edit().putString("SAVED_TITLE", value).apply()

    var savedCategory: String?
        get() = pref.getString("SAVED_CATEGORY", "")
        set(value) = pref.edit().putString("SAVED_CATEGORY", value).apply()

    var savedPrice: String?
        get() = pref.getString("SAVED_PRICE", "")
        set(value) = pref.edit().putString("SAVED_PRICE", value).apply()

    var savedPriceNegotiable: Boolean?
        get() = pref.getBoolean("SAVED_PRICE_NEGOTIABLE", false)
        set(value) = pref.edit().putBoolean("SAVED_PRICE_NEGOTIABLE", value?:false).apply()

    var savedContent: String?
        get() = pref.getString("SAVED_CONTENT", "")
        set(value) = pref.edit().putString("SAVED_CONTENT", value).apply()
}