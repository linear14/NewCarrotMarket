package com.dongldh.carrot.manager

import android.content.Context
import android.graphics.Point
import android.util.TypedValue
import android.view.WindowManager
import com.dongldh.carrot.util.App

object CarrotWindowManager {
    fun getDeviceSizeXY(): Point {
        val windowManager =
            App.applicationContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val deviceSize = Point()
        windowManager.defaultDisplay.apply {
            getSize(deviceSize)
        }

        return deviceSize
    }

    fun getToolbarHeight(): Int {
        var actionBarHeight = 100
        val tv = TypedValue()
        if (App.applicationContext().theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, App.applicationContext().resources.displayMetrics)
        }

        return actionBarHeight
    }
}