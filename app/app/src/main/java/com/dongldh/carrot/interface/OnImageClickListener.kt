package com.dongldh.carrot.`interface`

import com.dongldh.carrot.data.MediaStoreImage

interface OnImageClickListener {
    fun onClickImageLayout(position: Int)
    fun onClickImageBadge(image: MediaStoreImage)
}