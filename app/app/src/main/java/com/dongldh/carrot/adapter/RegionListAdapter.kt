package com.dongldh.carrot.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dongldh.carrot.data.Region
import com.dongldh.carrot.databinding.ItemRegionListBinding
import com.dongldh.carrot.util.App
import com.dongldh.carrot.util.UID_DETACHED

class RegionListAdapter(val listener: OnRegionSelectedListener) : PagedListAdapter<Region, RecyclerView.ViewHolder>(RegionDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RegionListViewHolder(ItemRegionListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val region = getItem(position)

        if(region != null) {
            (holder as RegionListViewHolder).bind(region)
        }
    }

    override fun getItemId(position: Int): Long {
        getItem(position)?.let { return it.id } ?: return NO_ITEM_AT_THIS_POSITION
    }

    inner class RegionListViewHolder(val binding: ItemRegionListBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            // preference에 uid 정보가 저장되어 있다면 로그인이 되어있는 상태라는 의미
            binding.setClickListener { _ ->
                if(App.pref.uid != UID_DETACHED) {

                } else {
                    listener.regionSelected(binding.region!!.id)
                }
            }
        }
        fun bind(item: Region) {
            binding.apply {
                region = item
                executePendingBindings()
            }
        }
    }

    interface OnRegionSelectedListener {
        fun regionSelected(regionId: Long)
    }

    companion object {
        private const val NO_ITEM_AT_THIS_POSITION = -1000L
    }
}

class RegionDiffCallback: DiffUtil.ItemCallback<Region>() {
    override fun areItemsTheSame(oldItem: Region, newItem: Region): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Region, newItem: Region): Boolean {
        return oldItem == newItem
    }

}

