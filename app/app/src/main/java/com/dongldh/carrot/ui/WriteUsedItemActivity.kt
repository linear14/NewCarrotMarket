package com.dongldh.carrot.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dongldh.carrot.R
import com.dongldh.carrot.`interface`.OnFinishItemNetworkingListener
import com.dongldh.carrot.adapter.ImageAdapter
import com.dongldh.carrot.data.Item
import com.dongldh.carrot.data.MediaStoreImage
import com.dongldh.carrot.data.NO_PRICE
import com.dongldh.carrot.firebase.ItemFirestore
import com.dongldh.carrot.util.App
import com.dongldh.carrot.util.FROM_WRITE_USED_ITEM
import com.dongldh.carrot.util.Util
import com.dongldh.carrot.util.setImageTint
import kotlinx.android.synthetic.main.activity_write_used_item.*

class WriteUsedItemActivity : AppCompatActivity(), View.OnClickListener {
    var isPriceNegotiable = false
    val adapter: ImageAdapter by lazy { ImageAdapter() }
    var images = listOf<MediaStoreImage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_used_item)

        if(isSavedState()) {
            App.pref.isSavedState = false
            initWhenSavedState()
        }

        current_count_image.text = 0.toString()
        text_current_region.text = App.pref.selectedRegionPair.second

        recycler_image.adapter = adapter

        layout_category.setOnClickListener(this)
        layout_suggest_price.setOnClickListener(this)
        action_add_image.setOnClickListener(this)
        action_next.setOnClickListener(this)
        action_back.setOnClickListener(this)

        input_price.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.isNullOrEmpty()) text_won.setTextColor(ContextCompat.getColor(this@WriteUsedItemActivity, R.color.colorDefaultText))
                else text_won.setTextColor(ContextCompat.getColor(this@WriteUsedItemActivity, R.color.colorHint))
            }
        })
    }

    private fun addItemToFirebase() {
        ItemFirestore.addItem(
            Item(
                title = input_title.text.toString(),
                category = text_category.text.toString(),
                price = if(input_price.text.isNullOrEmpty()) NO_PRICE else input_price.text.toString().toInt(),
                priceNegotiable = isPriceNegotiable,
                content = input_content.text.toString(),
                regionString = App.pref.selectedRegionPair.second,
                regionId = App.pref.selectedRegionPair.first,
                imageUri = mapMediaStoreImageToUri()
            ),
            object: OnFinishItemNetworkingListener {
                override fun onSuccess(item: Item?) {
                    finish()
                    Util.toastShort(resources.getString(R.string.success_enroll_item))
                }

                override fun onFailure() {
                    Util.toastShort(resources.getString(R.string.fail_enroll_item))
                }
            }
        )
    }

    private fun saveItemTemp() {
        App.pref.isSavedState = true
        App.pref.savedTitle = input_title.text.toString()
        App.pref.savedCategory = text_category.text.toString()
        App.pref.savedPrice = input_price.text.toString()
        App.pref.savedPriceNegotiable = isPriceNegotiable
        App.pref.savedContent = input_content.text.toString()
    }

    private fun isSavedState(): Boolean = App.pref.isSavedState?:false

    private fun setPriceNegotiableLayoutStyle(isNegotiable: Boolean) {
        if(isNegotiable) {
            image_suggest_price.setImageTint(R.color.colorDefaultText)
            text_suggest_price.setTextColor(ContextCompat.getColor(this@WriteUsedItemActivity, R.color.colorDefaultText))
        } else {
            image_suggest_price.setImageTint(R.color.colorHint)
            text_suggest_price.setTextColor(ContextCompat.getColor(this@WriteUsedItemActivity, R.color.colorHint))
        }
    }

    private fun initWhenSavedState() {
        input_title.setText(App.pref.savedTitle)
        text_category.text = App.pref.savedCategory
        input_price.setText(App.pref.savedPrice)
        setPriceNegotiableLayoutStyle(App.pref.savedPriceNegotiable!!)
        input_content.setText(App.pref.savedContent)
    }

    private fun isWritten() =
        input_title.text.isNotEmpty() || text_category.text.toString() != "카테고리" ||
                input_price.text.isNotEmpty() || input_content.text.isNotEmpty()


    private fun verifyItemFilledAndSaveItemTemp() {
        if(isWritten()) {
            saveItemTemp()
            Util.toastShort(resources.getString(R.string.msg_save_post_temp))
        }
        finish()
    }

    override fun onBackPressed() {
        verifyItemFilledAndSaveItemTemp()
        super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            FROM_WRITE_USED_ITEM -> {
                if(resultCode == Activity.RESULT_OK) {
                    val tempImages = data?.getParcelableArrayExtra("IMAGE_SET")?: arrayOf()
                    images = tempImages.map { it as MediaStoreImage }

                    current_count_image.text = images.size.toString()
                    adapter.submitList(images.toList())
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            layout_category -> {
                val builder = AlertDialog.Builder(this).apply {
                    setItems(R.array.categories) { _, pos ->
                        val items = resources.getStringArray(R.array.categories)
                        text_category.text = items[pos]
                    }
                }
                builder.create().apply { show() }
            }

            layout_suggest_price -> {
                isPriceNegotiable = !isPriceNegotiable
                setPriceNegotiableLayoutStyle(isPriceNegotiable)
            }

            action_add_image -> {
                val intent = Intent(this, ImagePickerActivity::class.java).apply {
                    putExtra("IMAGE_SET", images.toTypedArray())
                }
                startActivityForResult(intent, FROM_WRITE_USED_ITEM)
            }
            action_next -> {
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

            action_back -> { verifyItemFilledAndSaveItemTemp() }

        }
    }

    private fun mapMediaStoreImageToUri(): List<String> {
        return images.map { it.uri }
    }
}