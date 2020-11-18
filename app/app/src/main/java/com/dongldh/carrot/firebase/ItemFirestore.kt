package com.dongldh.carrot.firebase

import com.dongldh.carrot.`interface`.OnFinishItemNetworkingListener
import com.dongldh.carrot.data.Item
import com.dongldh.carrot.util.COLLECTION_ITEMS

object ItemFirestore {

    fun addItem(item: Item, li: OnFinishItemNetworkingListener) {
        UserFirestore.db.collection(COLLECTION_ITEMS)
            .document()
            .set(item)
            .addOnSuccessListener { li.onSuccess() }
            .addOnFailureListener{ li.onFailure() }
    }
}