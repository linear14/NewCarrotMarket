package com.dongldh.carrot.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.dongldh.carrot.R
import com.dongldh.carrot.`interface`.OnFinishNetworkingListener
import com.dongldh.carrot.data.Item
import com.dongldh.carrot.data.NO_PRICE
import com.dongldh.carrot.firebase.ItemFirestore
import com.dongldh.carrot.util.App
import com.dongldh.carrot.util.Util
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_write_used_item.*

class WriteUsedItemActivity : AppCompatActivity() {

    var storage: FirebaseStorage? = null
    var isPriceNegotiable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_used_item)

        storage = FirebaseStorage.getInstance()

        text_current_region.text = App.pref.regionSelected.second

        layout_category.setOnClickListener {
            val builder = AlertDialog.Builder(this).apply {
                setItems(R.array.categories) { _, pos ->
                    val items = resources.getStringArray(R.array.categories)
                    text_category.text = items[pos]
                }
            }
            builder.create().apply{ show() }
        }

        input_price.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.isNullOrEmpty()) text_won.setTextColor(ContextCompat.getColor(this@WriteUsedItemActivity, R.color.colorDefaultText))
                else text_won.setTextColor(ContextCompat.getColor(this@WriteUsedItemActivity, R.color.colorHint))
            }
        })

        layout_suggest_price.setOnClickListener {
            isPriceNegotiable = !isPriceNegotiable

            if(isPriceNegotiable) {
                DrawableCompat.setTint(
                    DrawableCompat.wrap(image_suggest_price.drawable),
                    ContextCompat.getColor(this, R.color.colorPrimary)
                )
                text_suggest_price.setTextColor(ContextCompat.getColor(this@WriteUsedItemActivity, R.color.colorDefaultText))
            } else {
                DrawableCompat.setTint(
                    DrawableCompat.wrap(image_suggest_price.drawable),
                    ContextCompat.getColor(this, R.color.colorHint)
                )
                text_suggest_price.setTextColor(ContextCompat.getColor(this@WriteUsedItemActivity, R.color.colorHint))
            }
        }

        action_next.setOnClickListener {
            when {
                input_title.text.isNullOrEmpty() -> {
                    Util.snackBarShort(layout_main, resources.getString(R.string.input_title_blank))
                }
                text_category.text.toString() == "카테고리" -> {
                    Util.snackBarShort(layout_main, resources.getString(R.string.input_category_blank))
                }
                input_content.text.isNullOrEmpty() -> {
                    Util.snackBarShort(layout_main, resources.getString(R.string.input_content_blank))
                }
                else -> { addItemToFirebase() }
            }

        }

        action_back.setOnClickListener { finish() }
    }

    private fun addItemToFirebase() {
        ItemFirestore.addItem(
            Item(
                title = input_title.text.toString(),
                category = text_category.text.toString(),
                price = if(input_price.text.isNullOrEmpty()) NO_PRICE else input_price.text.toString().toInt(),
                priceNegotiable = isPriceNegotiable,
                content = input_content.text.toString(),
                regionString = App.pref.regionSelected.second,
                regionId = App.pref.regionSelected.first
            ),
            object: OnFinishNetworkingListener {
                override fun onSuccess() {
                    finish()
                    Util.toastShort(resources.getString(R.string.success_enroll_item))
                }

                override fun onFailure() {
                    Util.toastShort(resources.getString(R.string.fail_enroll_item))
                }
            }
        )
    }
}