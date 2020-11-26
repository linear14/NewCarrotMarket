package com.dongldh.carrot.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dongldh.carrot.R
import com.dongldh.carrot.data.Item
import com.dongldh.carrot.databinding.ActivityItemDetailBinding
import com.dongldh.carrot.util.Util

class ItemDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_item_detail)

        Util.makeStatusBarTransparent(window)

        val binding = DataBindingUtil.setContentView<ActivityItemDetailBinding>(
            this,
            R.layout.activity_item_detail
        )
        binding.item = intent.getParcelableExtra<Item>("ITEM_INFO")


    }
}