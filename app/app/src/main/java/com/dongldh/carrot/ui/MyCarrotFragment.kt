package com.dongldh.carrot.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.dongldh.carrot.R
import com.dongldh.carrot.databinding.FragmentMyCarrotBinding
import com.dongldh.carrot.util.App
import com.dongldh.carrot.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf

class MyCarrotFragment: Fragment() {
    private val currentUserViewModel : UserViewModel by sharedViewModel{ parametersOf(App.pref.uid) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMyCarrotBinding>(inflater, R.layout.fragment_my_carrot, container, false)
            .apply {
                layoutSetting.setOnClickListener {
                    startActivity(Intent(requireContext(), AppSettingActivity::class.java))
                }
            }

        currentUserViewModel.currentUser.observe(requireActivity()) { result ->
            binding.textNickname.text = result.nickname
            binding.textRegion.text = getSelectedRegionName()
        }

        return binding.root
    }

    private fun getSelectedRegionName(): String = App.pref.regionSelected.second
}