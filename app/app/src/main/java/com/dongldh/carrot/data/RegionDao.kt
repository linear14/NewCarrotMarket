package com.dongldh.carrot.data

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RegionDao {
    @Query("SELECT * FROM regions ORDER BY name")
    fun selectAllRegions(): DataSource.Factory<Int, Region>

    @Query("SELECT * FROM regions WHERE name LIKE :str ORDER BY name")
    fun selectRegionsByString(str: String): DataSource.Factory<Int, Region>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRegions(regions: List<Region>)
}