package com.dongldh.carrot.data

import com.dongldh.carrot.util.App
import java.util.*

const val NO_PRICE = -1

data class Item (
    val uid: String? = App.pref.uid,
    val timeStamp: Long = Calendar.getInstance().timeInMillis,
    val title: String,
    val category: String,
    val price: Int = NO_PRICE,
    val priceNegotiable: Boolean,
    val content: String,
    val imageUrl: List<String>? = mutableListOf(),
    val regionString: String,
    val regionId: Long
){}