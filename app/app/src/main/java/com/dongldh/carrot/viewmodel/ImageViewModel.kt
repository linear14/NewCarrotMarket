package com.dongldh.carrot.viewmodel

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.dongldh.carrot.data.MediaStoreImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ImageViewModel(private val app: Application) : AndroidViewModel(app) {

    val images: MutableLiveData<List<MediaStoreImage>> = MutableLiveData()
    val selectedImagesLiveData: MutableLiveData<List<MediaStoreImage>> = MutableLiveData()
    private val selectedImages = mutableListOf<MediaStoreImage>()

    fun getImageList() {
        GlobalScope.launch {
            val imageList = queryImages(app.contentResolver)
            images.postValue(imageList)
        }
    }

    fun addOrRemoveImageFromSelectedList(image: MediaStoreImage) {
        if(image in selectedImages) {
            selectedImages.remove(image)
        } else {
            selectedImages.add(image)
        }
        selectedImagesLiveData.value = selectedImages
    }

    private suspend fun queryImages(contentResolver: ContentResolver): MutableList<MediaStoreImage>{
        val imageList = mutableListOf<MediaStoreImage>()

        withContext(Dispatchers.IO) {
            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATE_TAKEN
            )
            val selection = null
            val selectionArgs = null
            val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"

            contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val dateTakenColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)

                while(cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val dateTaken = Date(cursor.getLong(dateTakenColumn))
                    val uri = Uri.withAppendedPath(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id.toString()
                    )

                    val image = MediaStoreImage(id, dateTaken, uri.toString())
                    imageList += image

                    Log.d("IMAGE_INFO", image.toString())
                }
            }
        }
        return imageList
    }
}
