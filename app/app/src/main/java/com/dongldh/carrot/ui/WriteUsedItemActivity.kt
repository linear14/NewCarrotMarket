package com.dongldh.carrot.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.dongldh.carrot.R
import com.dongldh.carrot.`interface`.OnFinishNetworkingListener
import com.dongldh.carrot.adapter.ImageAdapter
import com.dongldh.carrot.data.Item
import com.dongldh.carrot.data.MediaStoreImage
import com.dongldh.carrot.data.NO_PRICE
import com.dongldh.carrot.firebase.ItemFirestore
import com.dongldh.carrot.util.App
import com.dongldh.carrot.util.FROM_WRITE_USED_ITEM
import com.dongldh.carrot.util.Util
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_write_used_item.*

class WriteUsedItemActivity : AppCompatActivity() {

    var storage: FirebaseStorage? = null
    var isPriceNegotiable = false
    val adapter: ImageAdapter by lazy { ImageAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_used_item)

        storage = FirebaseStorage.getInstance()

        if(isSavedState()) {
            App.pref.isSavedState = false
            initWhenSavedState()
        }

        current_count_image.text = 0.toString()
        text_current_region.text = App.pref.regionSelected.second

        recycler_image.adapter = adapter

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
            setPriceNegotiableLayoutStyle(isPriceNegotiable)
        }

        action_add_image.setOnClickListener {
            startActivityForResult(Intent(this, ImagePickerActivity::class.java), FROM_WRITE_USED_ITEM)
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

        action_back.setOnClickListener { verifyItemFilledAndSaveItemTemp() }
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
                    val images = tempImages.map { it as MediaStoreImage }

                    current_count_image.text = images.size.toString()
                    adapter.submitList(images.toList())
                }
            }
        }
    }
}