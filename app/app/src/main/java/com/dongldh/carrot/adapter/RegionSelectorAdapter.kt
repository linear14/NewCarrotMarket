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

                // TODO 지금 second(String) 으로 되어있는데, 이 부분의 SharedPreference도 현재 선택된 Pair쌍으로 만들어서 후에 id로 처리하는 작업을 하는것을 고려하자
                if(item.second == App.pref.regionSelected) {
                    textRegion.setTextSize(TypedValue.COMPLEX_UNIT_SP,16f)
                    textRegion.setTypeface(null, Typeface.BOLD)
                }

                executePendingBindings()
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

