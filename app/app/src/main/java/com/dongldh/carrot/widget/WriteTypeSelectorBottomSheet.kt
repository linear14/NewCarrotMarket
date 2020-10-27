package com.dongldh.carrot.widget

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dongldh.carrot.R
import com.dongldh.carrot.ui.WriteUsedItemActivity
import com.dongldh.carrot.util.App
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottomsheet_write_type_selector.view.*

class WriteTypeSelectorBottomSheet: BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottomsheet_write_type_selector, container, false)

        view.layout_sell_used_item.setOnClickListener {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            if(App.pref.isSavedState!!) {
                DialogVertical(requireActivity())
                    .setMessage(resources.getString(R.string.dialog_have_saved))
                    .setNegativeButton(resources.getString(R.string.negative_have_saved)) {
                        App.pref.isSavedState = false
                        startWriteUsedItemActivityAndDismiss()
                    }
                    .setPositiveButton(resources.getString(R.string.positive_have_saved)) {
                        startWriteUsedItemActivityAndDismiss()
                    }
                    .show()
            } else {
                startWriteUsedItemActivityAndDismiss()
            }
        }

        return view
    }

    private fun startWriteUsedItemActivityAndDismiss() {
        val intent = Intent(requireContext(), WriteUsedItemActivity::class.java)
        startActivity(intent)
        dismiss()
    }
}