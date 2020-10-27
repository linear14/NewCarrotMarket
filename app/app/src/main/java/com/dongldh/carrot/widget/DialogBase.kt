package com.dongldh.carrot.widget

import android.content.Context
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.dongldh.carrot.R
import kotlinx.android.synthetic.main.dialog_base_format.view.*

open class DialogBase(private val context: Context) {

    private val builder: AlertDialog.Builder by lazy {
        AlertDialog.Builder(context).setView(view)
    }

    protected open val view: View by lazy {
        View.inflate(context, R.layout.dialog_base_format, null)
    }

    protected var dialog: AlertDialog? = null

    private val onTouchListener = View.OnTouchListener { _, motionEvent ->
        if (motionEvent.action == MotionEvent.ACTION_UP) {
            Handler().postDelayed({
                dismiss()
            }, 5)
        }
        false
    }

    fun setMessage(@StringRes messageId: Int): DialogBase {
        view.dialog_msg.text = context.getText(messageId)
        return this
    }

    fun setMessage(message: CharSequence): DialogBase {
        view.dialog_msg.text = message
        return this
    }

    fun setPositiveButton(@StringRes textId: Int, listener: (view: View) -> (Unit)): DialogBase {
        view.dialog_ok.apply {
            text = context.getText(textId)
            setOnClickListener(listener)
            setOnTouchListener(onTouchListener)
        }
        return this
    }

    fun setPositiveButton(text: CharSequence, listener: (view: View) -> (Unit)): DialogBase {
        view.dialog_ok.apply {
            this.text = text
            setOnClickListener(listener)
            setOnTouchListener(onTouchListener)
        }
        return this
    }

    fun setNegativeButton(@StringRes textId: Int, listener: (view: View) -> (Unit)): DialogBase {
        view.dialog_cancel.apply {
            text = context.getText(textId)
            this.text = text
            setOnClickListener(listener)
            setOnTouchListener(onTouchListener)
        }
        return this
    }

    fun setNegativeButton(text: CharSequence, listener: (view: View) -> (Unit)): DialogBase {
        view.dialog_cancel.apply {
            this.text = text
            setOnClickListener(listener)
            setOnTouchListener(onTouchListener)
        }
        return this
    }

    fun create() {
        dialog = builder.create()
    }

    fun show() {
        dialog = builder.create()
        dialog?.show()
    }

    fun dismiss() {
        dialog?.dismiss()
    }

}