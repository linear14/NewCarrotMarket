package com.dongldh.carrot.ui

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.observe
import com.dongldh.carrot.R
import com.dongldh.carrot.util.*
import com.dongldh.carrot.viewmodel.SetMyRegionViewModel
import com.dongldh.carrot.widget.DialogBase
import kotlinx.android.synthetic.main.activity_set_my_region.*
import kotlinx.android.synthetic.main.activity_set_my_region.action_back
import kotlinx.android.synthetic.main.activity_write_used_item.*
import org.koin.androidx.viewmodel.ext.android.viewModel

const val REGION_EMPTY = 0
const val REGION_SELECTED = 1
const val REGION_NOT_SELECTED = 2

class SetMyRegionActivity : AppCompatActivity() {
    val setMyRegionViewModel: SetMyRegionViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_my_region)

        setMyRegionViewModel.regionPairList.observe(this) { regions ->
            setTextRegionList(regions)
        }

        setMyRegionViewModel.selectedRegionPair.observe(this) {
            setCardStyle()
        }

        layout_first_region.setOnClickListener { selectOrSetRegion(0) }
        layout_second_region.setOnClickListener { selectOrSetRegion(1) }
        action_delete_first_region.setOnClickListener { if(getRegionListSize() == 1) showUpdateRegionDialog() else showDeleteRegionDialog(notDeletedPos = 1) }
        action_delete_second_region.setOnClickListener { showDeleteRegionDialog(notDeletedPos = 0) }
        action_back.setOnClickListener { finish() }
    }

    private fun setTextRegionList(regions: List<Pair<Long, String>>) {
        when(regions.size) {
            1 -> { text_first_region.text = regions[0].second }
            2 -> {
                text_first_region.text = regions[0].second
                text_second_region.text = regions[1].second
            }
            else -> throw Exception()
        }
    }

    private fun setCardStyle() {
        when(setMyRegionViewModel.getSelectedPosition()) {
            0 -> {
                when(getRegionListSize()) {
                    1 -> setAllRegionCardStyle(REGION_SELECTED, REGION_EMPTY)
                    2 -> setAllRegionCardStyle(REGION_SELECTED, REGION_NOT_SELECTED)
                    else -> throw Exception()
                }
            }
            1 -> setAllRegionCardStyle(REGION_NOT_SELECTED, REGION_SELECTED)
            else -> throw Exception()
        }
    }

    private fun setAllRegionCardStyle(typeFirst: Int, typeSecond: Int) {
        setEachRegionCardStyle(layout_first_region, text_first_region, action_delete_first_region, typeFirst)
        setEachRegionCardStyle(layout_second_region, text_second_region, action_delete_second_region, typeSecond)
    }

    private fun setEachRegionCardStyle(layout: ConstraintLayout, text: TextView, delImage: ImageView, type: Int) {
        setBackground(layout, type)
        setTextVisibilityAndColor(text, type)
        setImageVisibilityAndTint(delImage, type)

        if(type == REGION_EMPTY) image_add.visibility = View.VISIBLE
        else image_add.visibility = View.GONE
    }

    private fun setBackground(view: ConstraintLayout, type: Int) {
        when(type) {
            REGION_EMPTY -> { view.setBackgroundResource(R.drawable.decorate_border_colorseparatorlight_solid_colordeepsurface_round) }
            REGION_SELECTED -> { view.setBackgroundResource(R.drawable.decorate_border_colorprimary_solid_colorprimary_round) }
            REGION_NOT_SELECTED -> { view.setBackgroundResource(R.drawable.decorate_border_colorseparatorlight_solid_colorsurface_round) }
        }
    }

    private fun setTextVisibilityAndColor(view: TextView, type: Int) {
        when(type) {
            REGION_EMPTY -> { view.visibility = View.GONE }
            REGION_SELECTED -> {
                view.visibility = View.VISIBLE
                view.setTextColor(ContextCompat.getColor(App.applicationContext(), R.color.colorWhite))}
            REGION_NOT_SELECTED -> {
                view.visibility = View.VISIBLE
                view.setTextColor(ContextCompat.getColor(App.applicationContext(), R.color.colorDefaultText))
            }
        }
    }

    private fun setImageVisibilityAndTint(view: ImageView, type: Int) {
        when(type) {
            REGION_EMPTY -> { view.visibility = View.GONE }
            REGION_SELECTED -> {
                view.visibility = View.VISIBLE
                view.setImageTint(R.color.colorWhite)
            }
            REGION_NOT_SELECTED -> {
                view.visibility = View.VISIBLE
                view.setImageTint(R.color.colorGray)
            }
        }
    }

    private fun selectOrSetRegion(position: Int) {
        when(App.pref.regionPairList.size) {
            1 -> {
                if(position == 1) {
                    val intent = Intent(this, RegionListActivity::class.java)
                    intent.putExtra(INTENT_TYPE, SET_SECOND_REGION)
                    startActivity(intent)
                }
            }
            2 -> {
                val selectedPair = App.pref.regionPairList[position]
                setMyRegionViewModel.updateSelectedRegion(selectedPair)
            }
        }
    }

    private fun showUpdateRegionDialog() {
        DialogBase(this)
            .setMessage(resources.getString(R.string.dialog_update_region))
            .setNegativeButton(resources.getString(R.string.negative_cancel)) {}
            .setPositiveButton(resources.getString(R.string.positive_change)) {
                val intent = Intent(this, RegionListActivity::class.java)
                intent.putExtra(INTENT_TYPE, CHANGE_FIRST_REGION)
                startActivity(intent)
            }
            .show()
    }

    private fun showDeleteRegionDialog(notDeletedPos: Int) {
        DialogBase(this)
            .setMessage(resources.getString(R.string.dialog_delete_region))
            .setNegativeButton(resources.getString(R.string.negative_cancel)) {}
            .setPositiveButton(resources.getString(R.string.positive_ok)) {
                val notDeletedRegion = App.pref.regionPairList[notDeletedPos]
                setMyRegionViewModel.deleteRegion(remainRegion = notDeletedRegion)
            }
            .show()
    }

    private fun getRegionListSize(): Int {
        setMyRegionViewModel.regionPairList.value?.let {
            return it.size
        } ?: return NO_REGION_DATA.toInt()
    }

    override fun onResume() {
        super.onResume()
        setMyRegionViewModel.initLiveData()
    }
}


