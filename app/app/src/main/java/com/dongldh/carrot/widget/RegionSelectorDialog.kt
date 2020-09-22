package com.dongldh.carrot.widget

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.dongldh.carrot.R
import com.dongldh.carrot.`interface`.OnDialogFragmentDismissListener
import com.dongldh.carrot.util.Util
import kotlinx.android.synthetic.main.dialog_region_selector.view.*

class RegionSelectorDialog : DialogFragment() {
    var dialogFragmentDismissListener: OnDialogFragmentDismissListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_region_selector, container, false)
        dialogInitUI()
        return view
    }

    override fun onDismiss(dialog: DialogInterface) {
        dialogFragmentDismissListener?.onDismiss() ?: Util.toastExceptionalError()
    }

    fun setOnDialogFragmentDismissListener(li: OnDialogFragmentDismissListener) {
        dialogFragmentDismissListener = li
    }

    private fun dialogInitUI() {
        dialog?.window?.apply {
            setDimAmount(0f)
            setGravity(Gravity.TOP or Gravity.START)
            attributes?.apply {
                x = 5
                y = 54
                attributes = this
            }
        } ?: Util.toastExceptionalError()
    }
}
