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
import com.dongldh.carrot.data.UserCreateAccountRequest
import com.dongldh.carrot.databinding.ActivityRegionListBinding
import com.dongldh.carrot.firebase.UserAuth
import com.dongldh.carrot.manager.KeyBoardManager
import com.dongldh.carrot.util.*
import com.dongldh.carrot.viewmodel.RegionListViewModel
import org.koin.android.ext.android.inject

class RegionListActivity : AppCompatActivity(), RegionListAdapter.OnRegionSelectedListener {
    lateinit var binding: ActivityRegionListBinding
    private val regionListViewModel: RegionListViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityRegionListBinding>(this, R.layout.activity_region_list).apply {
            lifecycleOwner = this@RegionListActivity
        }

        doAdapterInitialSettingAndSubscribeUi()
        attachListeners()
    }

    private inner class SearchTextChanged: TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(str: CharSequence?, start: Int, before: Int, count: Int) {
            if(str.isNullOrEmpty()) {
                setTextSearchFilter("")
                sendFilteredTextToViewModel(STATE_BLANK)
            } else {
                val wordSearched = resources.getString(R.string.filtered_region_by_word).replace("s", str.toString())
                setTextSearchFilter(wordSearched)
                sendFilteredTextToViewModel(str.toString())
            }
        }
    }

    private fun doAdapterInitialSettingAndSubscribeUi() {
        val adapter = RegionListAdapter(this)
        adapter.setHasStableIds(true)
        binding.recyclerRegionList.adapter = adapter
        subscribeUi(adapter)
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
        regionListViewModel.filterTextLiveData.value = str
    }

    private fun returnToOnCreateState() {
        clearSearchBarText()
        setTextSearchFilter(resources.getString(R.string.entire_region))
        sendFilteredTextToViewModel(STATE_INIT)
    }

    private fun clearSearchBarText() {
        binding.inputToolbarSearchRegionList.text.clear()
    }

    private fun attachListeners() {
        binding.inputToolbarSearchRegionList.addTextChangedListener(SearchTextChanged())
        binding.btnSearchEntireRegionList.setOnClickListener { returnToOnCreateState() }
        binding.noResultLayoutRegionList.setOnClickListener {
            clearSearchBarText()
            KeyBoardManager.keyBoardShowUp(binding.inputToolbarSearchRegionList)
        }
    }

    override fun regionSelected(region: String) {
        val userAccountInfo = UserCreateAccountRequest(
            email = "${intent.getStringExtra(ACCOUNT_ID)}@carrot.com",
            password = intent.getStringExtra(ACCOUNT_PASSWORD)?:throw Exception(),
            nickName = intent.getStringExtra(ACCOUNT_NICKNAME)?:throw Exception(),
            region = region,
            profileImageUrl = intent.getStringExtra(ACCOUNT_PROFILE_IMAGE_URL)?:throw Exception()
        )

        UserAuth(this).createUserFirebaseAuth(userAccountInfo)
    }

}