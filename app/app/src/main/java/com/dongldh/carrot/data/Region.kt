package com.dongldh.carrot.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "regions")
data class Region(
    val name: String,
    val latitude: Int,
    val longitude: Int
) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0L
}