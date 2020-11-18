package com.dongldh.carrot.firebase

import android.content.Intent
import com.dongldh.carrot.R
import com.dongldh.carrot.`interface`.OnFinishUserNetworkingListener
import com.dongldh.carrot.data.User
import com.dongldh.carrot.ui.MainActivity
import com.dongldh.carrot.util.App
import com.dongldh.carrot.util.COLLECTION_USERS
import com.dongldh.carrot.util.Util
import com.dongldh.carrot.util.Util.getRegionPairList
import com.dongldh.carrot.util.Util.setUserRegionInfoToSharedPreference
import com.google.firebase.firestore.FirebaseFirestore

object UserFirestore {
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    /***
     *  새로 회원가입하는 User의 정보를 Firestore에 저장합니다.
     */
    fun addUserInfo(user: User) {
        db.collection(COLLECTION_USERS)
            .document(user.uid!!)
            .set(user)
            .addOnSuccessListener {
                setUserRegionInfoToSharedPreference(user)

                val intent = Intent(App.applicationContext(), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                App.applicationContext().startActivity(intent)

                Util.toastShort(App.applicationContext().resources.getString(R.string.firebase_create_account_ok))
            }
            .addOnFailureListener{
                Util.toastShort(App.applicationContext().resources.getString(R.string.firebase_auth_create_user_error))
            }
    }

    fun getUserInfoLiveDataByUid(uid: String, li: OnFinishUserNetworkingListener) {
        val userRef = db.collection(COLLECTION_USERS).document(uid)
        userRef.get().addOnSuccessListener {
            val user = it.toObject(User::class.java)?:User()
            li.onSuccess(user)
        }
    }

    fun updateUserOnlyRegionList(uid: String, regionIdAll: ArrayList<Long>, regionStringAll: ArrayList<String>, li: OnFinishUserNetworkingListener) {
        val userRef = db.collection(COLLECTION_USERS).document(uid)
        userRef.update(mapOf(
            "regionIdAll" to regionIdAll,
            "regionStringAll" to regionStringAll
        )).addOnSuccessListener {
            App.pref.regionList = getRegionPairList(regionIdAll, regionStringAll)
            li.onSuccess()
        }.addOnFailureListener {
            li.onFailure()
        }
    }

    fun updateSelectedRegion(uid: String, regionPair: Pair<Long, String>, li: OnFinishUserNetworkingListener) {
        val userRef = db.collection(COLLECTION_USERS).document(uid)
        userRef.update(mapOf(
            "regionIdSelected" to regionPair.first,
            "regionStringSelected" to regionPair.second
        )).addOnSuccessListener {
            App.pref.regionSelected = regionPair
            li.onSuccess()
        }.addOnFailureListener {
            li.onFailure()
        }
    }

    fun remainOnlyOneRegion(uid: String, remainRegion: Pair<Long, String>, li: OnFinishUserNetworkingListener) {
        val newRegionIdList = ArrayList<Long>(1).apply { add(remainRegion.first) }
        val newRegionStringList = ArrayList<String>(1).apply { add(remainRegion.second) }

        val userRef = db.collection(COLLECTION_USERS).document(uid)
        userRef.update(mapOf(
            "regionIdAll" to newRegionIdList,
            "regionStringAll" to newRegionStringList
        )).addOnSuccessListener {
            App.pref.regionList = getRegionPairList(newRegionIdList, newRegionStringList)
            App.pref.regionSelected = remainRegion
            li.onSuccess()
        }.addOnFailureListener {
            li.onFailure()
        }
    }



}