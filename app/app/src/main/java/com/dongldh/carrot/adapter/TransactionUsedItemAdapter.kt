package com.dongldh.carrot.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dongldh.carrot.data.Item
import com.dongldh.carrot.databinding.ItemTransactionUsedItemBinding

class TransactionUsedItemAdapter: ListAdapter<Item, TransactionUsedItemAdapter.TransactionUsedItemViewHolder>(TransactionUsedItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionUsedItemViewHolder {
        return TransactionUsedItemViewHolder(
           ItemTransactionUsedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TransactionUsedItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class TransactionUsedItemViewHolder(val binding: ItemTransactionUsedItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.apply {
                this.item = item
                executePendingBindings()
            }
        }
    }
}

class TransactionUsedItemDiffCallback: DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return (oldItem.uid == newItem.uid) && (oldItem.timeStamp == newItem.timeStamp)
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }

}