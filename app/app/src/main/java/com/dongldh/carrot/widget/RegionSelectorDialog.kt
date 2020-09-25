package com.dongldh.carrot.widget

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.dongldh.carrot.R
import com.dongldh.carrot.`interface`.OnDialogFragmentDismissListener
import com.dongldh.carrot.adapter.RegionSelectorAdapter
import com.dongldh.carrot.manager.CarrotWindowManager
import com.dongldh.carrot.util.App
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
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        val adapter = RegionSelectorAdapter()
        adapter.submitList(App.pref.regionList)
        view.recycler_region_selector.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()
        dialogInitUI()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dialogFragmentDismissListener?.onDismiss() ?: Util.toastExceptionalError()
    }

    fun setOnDialogFragmentDismissListener(li: OnDialogFragmentDismissListener) {
        dialogFragmentDismissListener = li
    }

    private fun dialogInitUI() {
        dialog?.window?.apply {
            setDimAmount(0f)
            setGravity(Gravity.TOP or Gravity.START)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setDialogPosition(this, posX = 15, posY = CarrotWindowManager.getToolbarHeight() - 30)
        } ?: Util.toastExceptionalError()
    }

    private fun setDialogPosition(window: Window, posX: Int, posY: Int) {
        val deviceSize = CarrotWindowManager.getDeviceSizeXY()

        window.apply {
            attributes?.apply {
                x = posX
                y = posY
                width = (deviceSize.x * 0.45).toInt()
                attributes = this
            }
        }
    }
}
