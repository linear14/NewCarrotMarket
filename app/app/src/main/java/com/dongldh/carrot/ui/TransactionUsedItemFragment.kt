package com.dongldh.carrot.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.dongldh.carrot.R
import com.dongldh.carrot.adapter.TransactionUsedItemAdapter
import com.dongldh.carrot.viewmodel.UsedItemViewModel
import kotlinx.android.synthetic.main.fragment_transaction_used_item.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TransactionUsedItemFragment: Fragment() {

    val usedItemViewModel: UsedItemViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val adapter = TransactionUsedItemAdapter()

        val view = inflater.inflate(R.layout.fragment_transaction_used_item, container, false).apply {
            recycler_used_item_card.adapter = adapter
        }

        usedItemViewModel.usedItemList.observe(requireActivity()) { result ->
            adapter.submitList(result)
        }

        return view
    }
}