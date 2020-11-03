package com.dongldh.carrot.data

import com.dongldh.carrot.util.App
import java.util.*

const val NO_PRICE = -1

data class Item (
    val uid: String? = App.pref.uid,
    val timeStamp: Long = Calendar.getInstance().timeInMillis,
    val title: String? = null,
    val category: String? = null,
    val price: Int = NO_PRICE,
    val priceNegotiable: Boolean? = null,
    val content: String? = null,
    val imageUrl: List<String>? = mutableListOf(),
    val regionString: String? = null,
    val regionId: Long? = null
){}