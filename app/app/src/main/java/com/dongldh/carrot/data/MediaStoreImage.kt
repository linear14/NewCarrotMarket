package com.dongldh.carrot.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class MediaStoreImage(
    val id: Long,
    val date: Date,
    val uri: String
): Parcelable {
}