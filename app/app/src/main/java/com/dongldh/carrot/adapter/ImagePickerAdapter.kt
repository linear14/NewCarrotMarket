package com.dongldh.carrot.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dongldh.carrot.R
import com.dongldh.carrot.`interface`.OnImageClickListener
import com.dongldh.carrot.data.MediaStoreImage
import com.dongldh.carrot.databinding.ItemImagePickerBinding

class ImagePickerAdapter: ListAdapter<MediaStoreImage, ImagePickerAdapter.ImageViewHolder>(ImageDiffCallback()) {
    private var imageClickListener: OnImageClickListener? = null
    var selectedImages = listOf<MediaStoreImage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(ItemImagePickerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val mediaStoreImage = getItem(position)!!
        holder.bind(mediaStoreImage)
    }

    inner class ImageViewHolder(val binding: ItemImagePickerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MediaStoreImage) {
            binding.apply {
                mediaStoreImage = item
                executePendingBindings()

                layoutImage.setOnClickListener { imageClickListener?.onClickImageLayout(layoutPosition) }
                layoutBadge.setOnClickListener { imageClickListener?.onClickImageBadge(item) }
            }
            bindUI(item)
        }

        private fun bindUI(mediaStoreImage: MediaStoreImage) {
            binding.apply {
                if(mediaStoreImage in selectedImages) {
                    numbering.text = (selectedImages.indexOf(mediaStoreImage) + 1).toString()
                    imageFilter.visibility = View.VISIBLE
                    layoutBadge.setBackgroundResource(R.drawable.decorate_orange_background)
                } else {
                    numbering.text = ""
                    imageFilter.visibility = View.GONE
                    layoutBadge.setBackgroundResource(R.drawable.decorate_white_circle)
                }
            }
        }
    }

    fun setOnImageClickListener(li: OnImageClickListener) {
        imageClickListener = li
    }
}

class ImageDiffCallback: DiffUtil.ItemCallback<MediaStoreImage>() {
    override fun areItemsTheSame(oldItem: MediaStoreImage, newItem: MediaStoreImage): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MediaStoreImage, newItem: MediaStoreImage): Boolean {
        return oldItem == newItem
    }

}