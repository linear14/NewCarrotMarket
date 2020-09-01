package com.dongldh.carrot.data

class RegionRepository (
    private val regionDao: RegionDao
) {
    fun selectAllRegions() = regionDao.selectAllRegions()
}