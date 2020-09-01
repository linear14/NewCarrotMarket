package com.dongldh.carrot.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RegionDao {
    @Query("SELECT * FROM regions")
    fun selectAllRegions(): List<Region>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRegions(regions: List<Region>)
}