package com.dongldh.carrot.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import com.dongldh.carrot.R

class DialogVertical(private val context: Context): DialogBase(context) {
    override val view: View by lazy {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        View.inflate(context, R.layout.dialog_vertical_format, null)
    }
}