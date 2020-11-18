package com.dongldh.carrot.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.dongldh.carrot.R
import com.dongldh.carrot.`interface`.OnImageClickListener
import com.dongldh.carrot.adapter.ImagePickerAdapter
import com.dongldh.carrot.data.MediaStoreImage
import com.dongldh.carrot.util.Permission.haveStoragePermission
import com.dongldh.carrot.util.Permission.requestPermission
import com.dongldh.carrot.util.Util
import com.dongldh.carrot.viewmodel.ImageViewModel
import kotlinx.android.synthetic.main.activity_image_picker.*

class ImagePickerActivity : AppCompatActivity() {

    companion object {
        const val READ_EXTERNAL_STORAGE_REQUEST = 0x1001
    }

    private lateinit var imageViewModel: ImageViewModel
    private lateinit var imageAdapter: ImagePickerAdapter

    var totalImageSize: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_picker)

        imageViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        ).get(ImageViewModel::class.java)

        imageAdapter = ImagePickerAdapter()
            .also { recycler.adapter = it }
            .apply {
                setOnImageClickListener(object: OnImageClickListener {
                    override fun onClickImageLayout(position: Int) {

                    }

                    override fun onClickImageBadge(image: MediaStoreImage) {
                        imageViewModel.addOrRemoveImageFromSelectedList(image)
                    }

                })
            }

        openMediaStore()

        action_next.setOnClickListener {
            if(imageViewModel.selectedImagesLiveData.value.isNullOrEmpty()) {
                Util.toastShort("선택된 사진이 없습니다")
            } else {
                returnDataToPreviousActivity()
            }
        }

        action_back.setOnClickListener { onBackPressed() }
    }

    private fun openMediaStore() {
        if(haveStoragePermission()) {
            observeImages()
            observeSelectedImages()
        } else {
            requestPermission(this)
        }
    }

    private fun observeImages() {
        imageViewModel.getImageList()
        imageViewModel.images.observe(this) { result ->
            imageAdapter.submitList(result)
            totalImageSize = result.size
        }
    }

    private fun observeSelectedImages() {
        imageViewModel.getSelectedImages(intent)
        imageViewModel.selectedImagesLiveData.observe(this) { result ->
            imageAdapter.selectedImages = result
            imageAdapter.notifyDataSetChanged()
        }
    }

    private fun goToSettings() {
        Intent(
            ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:$packageName")
        ).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }.also { startActivity(it) }
    }

    override fun onBackPressed() {
        if(frame_layout.visibility == View.VISIBLE) {
            frame_layout.visibility = View.GONE
        } else {
            super.onBackPressed()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            READ_EXTERNAL_STORAGE_REQUEST -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                    observeImages()
                    observeSelectedImages()
                } else {
                    val showRationale =
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    if(!showRationale) {
                        goToSettings()
                    }
                }
                return
            }
        }
    }

    private fun returnDataToPreviousActivity() {
        /*for(i in imageViewModel.selectedImagesLiveData.value!!.toTypedArray()) {
            Log.d("IMAGE_LOG", "id : ${i.id} __ uri : ${i.uri}")
        }*/
        val returnIntent = Intent()
        returnIntent.putExtra("IMAGE_SET", imageViewModel.selectedImagesLiveData.value?.toTypedArray())
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}