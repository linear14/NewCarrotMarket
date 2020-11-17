package com.dongldh.carrot.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dongldh.carrot.data.MediaStoreImage
import com.dongldh.carrot.databinding.ItemImageSelectedBinding

class ImageAdapter: ListAdapter<MediaStoreImage, ImageAdapter.ImageViewHolder>(ImageDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageAdapter.ImageViewHolder {
        return ImageViewHolder(ItemImageSelectedBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ImageAdapter.ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ImageViewHolder(val binding: ItemImageSelectedBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MediaStoreImage) {
            binding.apply {
                mediaStoreImage = item
                executePendingBindings()
            }
        }
    }

}
