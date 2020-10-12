package com.dongldh.carrot.widget

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dongldh.carrot.R
import com.dongldh.carrot.ui.WriteUsedItemActivity
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
            val intent = Intent(requireContext(), WriteUsedItemActivity::class.java)
            startActivity(intent)
            dismiss()
        }

        return view
    }
}