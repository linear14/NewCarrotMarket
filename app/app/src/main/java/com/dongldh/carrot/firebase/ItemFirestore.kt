package com.dongldh.carrot.firebase

import android.net.Uri
import android.util.Log
import com.dongldh.carrot.`interface`.OnFinishItemNetworkingListener
import com.dongldh.carrot.data.Item
import com.dongldh.carrot.util.COLLECTION_ITEMS
import com.dongldh.carrot.util.Util
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object ItemFirestore {
    var storage: FirebaseStorage = FirebaseStorage.getInstance()
    val storageRef = storage.reference

    fun addItem(item: Item, li: OnFinishItemNetworkingListener) {
        runBlocking {
            if(!item.imageUri.isNullOrEmpty()) {
                Log.d("WORK_FOR_IMAGE_UPLOAD", "이미지 업로드 작업 시작")
                uploadImages(item.imageUri)
            }
            Log.d("WORK_FOR_IMAGE_UPLOAD", "이미지 업로드 작업 종료")

            Log.d("WORK_FOR_IMAGE_UPLOAD", "파이어스토어 데이터 저장 작업 시작")
            UserFirestore.db.collection(COLLECTION_ITEMS)
                .document()
                .set(mapToServerItem(item))
                .addOnSuccessListener {
                    Log.d("WORK_FOR_IMAGE_UPLOAD", "파이어스토어 데이터 저장 작업 종료")
                    li.onSuccess()
                }
                .addOnFailureListener{ li.onFailure() }
        }
    }
    
    suspend fun uploadImages(uriList: List<String>) {
        val job = mutableListOf<Job>()
        
        for(i in uriList) {
            val uri = Uri.parse(i)
            val uploadTask = GlobalScope.launch {
                val imageRef = storageRef.child("itemImages/${uri.lastPathSegment}")
                imageRef.putFile(uri)
                Log.d("WORK_FOR_IMAGE_UPLOAD", "이미지 업로드작업(${uri.lastPathSegment})")
            }
            Log.d("WORK_FOR_IMAGE_UPLOAD", "이미지 업로드작업을 Job에 집어넣음(${uri.lastPathSegment})")
            job.add(uploadTask)
        }

        /*for(i in job) {
            i.join()
        }*/
        
        for((index, i) in job.withIndex()) {
            Log.d("WORK_FOR_IMAGE_UPLOAD", "이미지 업로드작업을 Join으로 대기($index)")
            i.join()
        }

        Log.d("WORK_FOR_IMAGE_UPLOAD", "이미지 업로드성공")
        Util.toastShort("이미지 업로드 성공")
    }

    private fun mapToServerItem(item: Item): Item =
        item.copy(
            imageUri = item.imageUri?.map {
                it.substring(it.lastIndexOf("/") + 1)
            }
        )
}