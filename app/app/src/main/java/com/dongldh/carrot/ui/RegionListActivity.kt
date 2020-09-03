package com.dongldh.carrot.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.dongldh.carrot.R
import com.dongldh.carrot.adapter.RegionListAdapter
import com.dongldh.carrot.databinding.ActivityRegionListBinding
import com.dongldh.carrot.manager.KeyBoardManager
import com.dongldh.carrot.util.STATE_INIT
import com.dongldh.carrot.viewmodel.RegionListViewModel
import org.koin.android.ext.android.inject

class RegionListActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegionListBinding
    private val regionListViewModel: RegionListViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityRegionListBinding>(this, R.layout.activity_region_list).apply {
            lifecycleOwner = this@RegionListActivity

            val adapter = RegionListAdapter()
            // adapter.setHasStableIds(true)   // 깜빡임을 막아준다. issue : 막아주긴 하는데, 전체 동네 목록 조회를 눌렀을 때 뷰가 이상하게 업데이트됨;;
            recyclerRegionList.adapter = adapter
            subscribeUi(adapter)

            inputToolbarSearchRegionList.addTextChangedListener(SearchTextChanged())
            btnSearchEntireRegionList.setOnClickListener { returnToOnCreateState() }
            noResultLayoutRegionList.setOnClickListener {
                clearSearchBarText()
                KeyBoardManager.keyBoardShowUp(inputToolbarSearchRegionList)
            }
        }

    }

    private fun subscribeUi(adapter: RegionListAdapter) {
        regionListViewModel.getRegionsLiveData()?.observe(this) { regions ->
            adapter.submitList(regions)

            if(regions.isNullOrEmpty()) {
                binding.noResultLayoutRegionList.visibility = View.VISIBLE
                binding.recyclerRegionList.visibility = View.GONE
            } else {
                binding.noResultLayoutRegionList.visibility = View.GONE
                binding.recyclerRegionList.visibility = View.VISIBLE
            }
        }
    }

    private fun setTextSearchFilter(str: String) {
        binding.textSearchFilterRegionList.text = str
    }

    private fun sendFilteredTextToViewModel(str: String) {
        regionListViewModel.filterText.value = str
    }

    private fun returnToOnCreateState() {
        sendFilteredTextToViewModel(STATE_INIT)
        clearSearchBarText()
        setTextSearchFilter(resources.getString(R.string.entire_region))
    }

    private fun clearSearchBarText() {
        binding.inputToolbarSearchRegionList.text.clear()
    }


    private inner class SearchTextChanged: TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(str: CharSequence?, start: Int, before: Int, count: Int) {
            if(str.isNullOrEmpty()) {
                setTextSearchFilter("")
                binding.noResultLayoutRegionList.visibility = View.VISIBLE
                binding.recyclerRegionList.visibility = View.GONE
            } else {
                val wordSearched = resources.getString(R.string.filtered_region_by_word).replace("s", str.toString())
                setTextSearchFilter(wordSearched)
                sendFilteredTextToViewModel(str.toString())
            }
        }

    }
}