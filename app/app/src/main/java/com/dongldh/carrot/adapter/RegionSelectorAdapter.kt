package com.dongldh.carrot.adapter

import android.graphics.Typeface
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dongldh.carrot.databinding.ItemRegionSelectorBinding
import com.dongldh.carrot.util.App

class RegionSelectorAdapter : ListAdapter<Pair<Long, String>, RecyclerView.ViewHolder>(RegionSelectorDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RegionSelectorViewHolder(ItemRegionSelectorBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val region = getItem(position)

        if(region != null) {
            (holder as RegionSelectorViewHolder).bind(region)
        }
    }

    inner class RegionSelectorViewHolder(val binding: ItemRegionSelectorBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Pair<Long, String>) {
            binding.apply {
                region = item.second
                accentuateSelectedRegion(item.first)

                executePendingBindings()
            }
        }

        private fun accentuateSelectedRegion(regionId: Long) {
            if(regionId == App.pref.regionSelected.first) {
                binding.textRegion.setTextSize(TypedValue.COMPLEX_UNIT_SP,16f)
                binding.textRegion.setTypeface(null, Typeface.BOLD)
            }
        }
    }

    interface OnRegionSelectedListener {
        fun regionSelected(region: String)
    }
}

class RegionSelectorDiffCallback: DiffUtil.ItemCallback<Pair<Long, String>>() {
    override fun areItemsTheSame(oldItem: Pair<Long, String>, newItem: Pair<Long, String>): Boolean {
        return oldItem.first == newItem.first
    }

    override fun areContentsTheSame(oldItem: Pair<Long, String>, newItem: Pair<Long, String>): Boolean {
        return oldItem == newItem
    }

}

