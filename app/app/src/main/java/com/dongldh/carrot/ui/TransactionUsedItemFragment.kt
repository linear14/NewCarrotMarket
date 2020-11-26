package com.dongldh.carrot.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.dongldh.carrot.R
import com.dongldh.carrot.`interface`.OnItemClickListener
import com.dongldh.carrot.adapter.TransactionUsedItemAdapter
import com.dongldh.carrot.data.Item
import com.dongldh.carrot.viewmodel.UsedItemViewModel
import kotlinx.android.synthetic.main.fragment_transaction_used_item.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TransactionUsedItemFragment: Fragment() {

    val usedItemViewModel: UsedItemViewModel by viewModel()
    val adapter: TransactionUsedItemAdapter by lazy { TransactionUsedItemAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =
            inflater.inflate(R.layout.fragment_transaction_used_item, container, false).apply {
                recycler_used_item_card.adapter = adapter
            }

        setItemClickListener()
        subscribeItem()

        return view
    }

    private fun setItemClickListener() {
        adapter.setOnItemClickListener(object: OnItemClickListener {
            override fun onClick(item: Item) {
                val intent = Intent(requireContext(), ItemDetailActivity::class.java)
                intent.putExtra("ITEM_INFO", item)

                startActivity(intent)
            }
        })
    }

    private fun subscribeItem() {
        usedItemViewModel.usedItemList.observe(requireActivity()) { result ->
            adapter.submitList(result)
        }
    }
}