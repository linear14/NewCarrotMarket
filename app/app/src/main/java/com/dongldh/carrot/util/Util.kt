package com.dongldh.carrot.util

import android.view.View
import android.widget.Toast
import com.dongldh.carrot.R
import com.dongldh.carrot.data.User
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_write_used_item.*

object Util {

    fun toastShort(msg: String) {
        Toast.makeText(App.applicationContext(), msg, Toast.LENGTH_SHORT).show()
    }

    fun toastLong(msg: String) {
        Toast.makeText(App.applicationContext(), msg, Toast.LENGTH_LONG).show()
    }

    fun showErrorToast() {
        toastShort(App.applicationContext().resources.getString(R.string.exceptional_error))
    }

    fun snackBarShort(view: View, msg: String) {
        Snackbar
            .make(view, msg, Snackbar.LENGTH_SHORT)
            .setTextColorWhite()
            .show()
    }

    fun getRegionPairList(idList: List<Long>, nameList: List<String>): ArrayList<Pair<Long, String>> {
        val list = arrayListOf<Pair<Long, String>>()
        for(i in idList.indices) {
            list.add(Pair(idList[i], nameList[i]))
        }
        return list
    }

    fun getRegionSelectedPair(selectedId: Long, selectedName: String): Pair<Long, String> =
        Pair(selectedId, selectedName)

}