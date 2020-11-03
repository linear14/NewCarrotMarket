package com.dongldh.carrot.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dongldh.carrot.ui.RegionLifeFragment
import com.dongldh.carrot.ui.TransactionUsedItemFragment

class MainFragmentAdapter(fragmentManager: FragmentManager, lifeCycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifeCycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> TransactionUsedItemFragment()
            else -> RegionLifeFragment()
        }
    }
}