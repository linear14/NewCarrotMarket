package com.dongldh.carrot.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dongldh.carrot.data.AppDatabase
import com.dongldh.carrot.data.Region
import com.dongldh.carrot.util.REGION_JSON
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.coroutineScope

class RegionDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
): CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope{
        try {
            applicationContext.assets.open(REGION_JSON).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val regionType = object: TypeToken<List<Region>>(){}.type
                    val regionList: List<Region> = Gson().fromJson(jsonReader, regionType)

                    val database = AppDatabase.getInstance(applicationContext)
                    database.regionDao().insertRegions(regionList)

                    Result.success()
                }
            }
        } catch (e: Exception) {
            Result.failure()
        }

    }
}
