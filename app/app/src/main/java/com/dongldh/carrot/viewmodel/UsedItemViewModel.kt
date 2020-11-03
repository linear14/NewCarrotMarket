package com.dongldh.carrot.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dongldh.carrot.data.Item
import com.dongldh.carrot.util.COLLECTION_ITEMS
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class UsedItemViewModel: ViewModel() {
    val db = FirebaseFirestore.getInstance()

    val usedItemList: MutableLiveData<List<Item>> = MutableLiveData()

    init {
        getUsedItemData()
    }

    fun getUsedItemData() {
        val itemList = arrayListOf<Item>()

        db.collection(COLLECTION_ITEMS)
            .orderBy("timeStamp", Query.Direction.DESCENDING)
            .addSnapshotListener { querySnapshot, exception ->
                itemList.clear()

                if(querySnapshot == null) return@addSnapshotListener

                for(snapshot in querySnapshot.documents) {
                    val item = snapshot.toObject(Item::class.java)
                    itemList.add(item!!)
                }
                usedItemList.value = itemList
            }
    }

}