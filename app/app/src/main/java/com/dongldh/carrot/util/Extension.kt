package com.dongldh.carrot.util

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.dongldh.carrot.R
import com.google.android.material.snackbar.Snackbar

fun Snackbar.setTextColorWhite(): Snackbar {
    val tv = view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    tv.setTextColor(ContextCompat.getColor(App.applicationContext(), R.color.colorWhite))

    return this
}

fun ImageView.setImageTint(color: Int) {
    DrawableCompat.setTint(
        DrawableCompat.wrap(this.drawable),
        ContextCompat.getColor(App.applicationContext(), color)
    )
}