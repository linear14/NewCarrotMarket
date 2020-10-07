package com.dongldh.carrot.firebase

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.dongldh.carrot.R
import com.dongldh.carrot.`interface`.OnFinishNetworkingListener
import com.dongldh.carrot.data.User
import com.dongldh.carrot.ui.MainActivity
import com.dongldh.carrot.util.App
import com.dongldh.carrot.util.COLLECTION_USERS
import com.dongldh.carrot.util.SharedUtil
import com.dongldh.carrot.util.Util
import com.google.firebase.firestore.FirebaseFirestore

object UserFirestoreManager {
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

    fun getUserInfoLiveDataByUid(uid: String, userLiveData: MutableLiveData<User>) {
        val userRef = db.collection(COLLECTION_USERS).document(uid)
        userRef.get().addOnSuccessListener {
            val user = it.toObject(User::class.java)
            userLiveData.value = user
        }
    }

    fun getUserInfoByUidAndSavedAccountInfoAndGoToMain(uid: String) {
        val userRef = db.collection(COLLECTION_USERS).document(uid)
        userRef.get().addOnSuccessListener {
            val user = it.toObject(User::class.java)!!
            setUserRegionInfoToSharedPreference(user)

            val intent = Intent(App.applicationContext(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            App.applicationContext().startActivity(intent)
        }
    }

    fun updateUserOnlyRegionList(uid: String, regionIdAll: ArrayList<Long>, regionStringAll: ArrayList<String>, li: OnFinishNetworkingListener) {
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

    fun updateSelectedRegion(uid: String, regionPair: Pair<Long,String>, li: OnFinishNetworkingListener) {
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

    private fun getRegionPairList(idList: List<Long>, nameList: List<String>): ArrayList<Pair<Long, String>> {
        val list = arrayListOf<Pair<Long, String>>()
        for(i in idList.indices) {
            list.add(Pair(idList[i], nameList[i]))
        }
        return list
    }

    private fun getRegionSelectedPair(selectedId: Long, selectedName: String): Pair<Long, String> =
        Pair(selectedId, selectedName)

    private fun setUserRegionInfoToSharedPreference(user: User) {
        val regionList = getRegionPairList(user.regionIdAll, user.regionStringAll)
        val selectedList = getRegionSelectedPair(user.regionIdSelected!!, user.regionStringSelected!!)
        SharedUtil.attachRegion(regionList, selectedList)
    }

}