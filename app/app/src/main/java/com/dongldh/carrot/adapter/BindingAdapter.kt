package com.dongldh.carrot.adapter

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.dongldh.carrot.R
import com.dongldh.carrot.data.NO_PRICE
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
        view.text = ""
    }
}