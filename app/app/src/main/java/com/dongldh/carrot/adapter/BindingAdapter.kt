package com.dongldh.carrot.adapter

import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dongldh.carrot.R
import com.dongldh.carrot.data.NO_PRICE
import com.dongldh.carrot.firebase.ItemFirestore.storage
import com.dongldh.carrot.util.App
import java.util.*

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter(value = ["bind:timeStamp"])
fun bindGetTimeUpdated(view: TextView, timeStamp: Long) {
    val gap = Calendar.getInstance().timeInMillis - timeStamp
    when(gap) {
        in 0 until (60 * 1000) -> view.text = "방금 전"
        in (60 * 1000) until (3600 * 1000) -> view.text = "${gap / (60 * 1000)}분 전"
        in (3600 * 1000) until (3600 * 24 * 1000) -> view.text = "${gap / (3600 * 1000)}시간 전"
        in (3600 * 24 * 1000) until (3600 * 24 * 14 * 1000) -> view.text = "${gap / (3600 * 24 * 1000)}일 전"
        else -> view.text = "오래 전"
    }
}

@BindingAdapter("price")
fun bindPrice(view: TextView, price: Int) {
    if(price != NO_PRICE) {
        view.text = "${price}원"
    } else {
        view.text = "가격 정보 없음"
    }
}

@BindingAdapter("imageFromUri")
fun bindImageFromUri(view: ImageView, imageUri: String?) {
    Glide.with(view.context)
        .load(Uri.parse(imageUri))
        .placeholder(ColorDrawable(ContextCompat.getColor(App.applicationContext(), android.R.color.white)))
        .transition(DrawableTransitionOptions.withCrossFade())
        .centerCrop()
        .into(view)
}

@BindingAdapter("itemImageFromUri")
fun bindItemImageFromUri(view: ImageView, imageUri: String?) {
    val gsReference = storage.getReferenceFromUrl("gs://carrot-69315.appspot.com/itemImages/$imageUri")
    gsReference.downloadUrl.addOnSuccessListener {
        Glide.with(view.context)
            .load(it)
            .placeholder(ColorDrawable(ContextCompat.getColor(App.applicationContext(), android.R.color.white)))
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .into(view)
    }
}

@BindingAdapter("userNameForOtherItem")
fun bindOtherItemOfUser(view: TextView, userName: String) {
    view.text = "${userName}님의 판매상품"
}

@BindingAdapter("negotiable")
fun bindIsNegotiable(view: TextView, isNegotiable: Boolean) {
    if(isNegotiable) {
        view.text = "가격제안하기"
        view.setTextColor(ContextCompat.getColor(App.applicationContext(), R.color.colorPrimary))
    } else {
        view.text = "가격제안불가"
        view.setTextColor(ContextCompat.getColor(App.applicationContext(), R.color.colorDescription))
    }
}