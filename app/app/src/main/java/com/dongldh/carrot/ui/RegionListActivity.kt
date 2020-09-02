package com.dongldh.carrot.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.dongldh.carrot.R
import com.dongldh.carrot.adapter.RegionListAdapter
import com.dongldh.carrot.databinding.ActivityRegionListBinding
import com.dongldh.carrot.viewmodel.RegionListViewModel
import org.koin.android.ext.android.inject

class RegionListActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegionListBinding
    private val regionListViewModel: RegionListViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_region_list)
        binding.lifecycleOwner = this

        val adapter = RegionListAdapter()
        binding.recyclerRegionList.adapter = adapter
        subscribeUi(adapter)
    }

    private fun subscribeUi(adapter: RegionListAdapter) {
        regionListViewModel.getRegionsLiveData().observe(this) { regions ->
            adapter.submitList(regions)
        }
    }
}