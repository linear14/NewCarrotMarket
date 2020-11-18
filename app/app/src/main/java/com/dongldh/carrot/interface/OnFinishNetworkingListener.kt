package com.dongldh.carrot.`interface`

import com.dongldh.carrot.data.Item
import com.dongldh.carrot.data.User

interface OnFinishUserNetworkingListener {
    fun onSuccess(user: User? = null)
    fun onFailure()
}

interface OnFinishItemNetworkingListener {
    fun onSuccess(item: Item? = null)
    fun onFailure()
}