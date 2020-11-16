package com.dongldh.carrot.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dongldh.carrot.ui.ImagePickerActivity

object Permission {
    fun requestPermission(activity: Activity) {
        if(!haveStoragePermission()) {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(activity, permissions,
                ImagePickerActivity.READ_EXTERNAL_STORAGE_REQUEST
            )
        }
    }

    fun haveStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            App.applicationContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }
}