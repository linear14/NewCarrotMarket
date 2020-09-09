package com.dongldh.carrot.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.dongldh.carrot.R
import com.dongldh.carrot.databinding.FragmentMyCarrotBinding

class MyCarrotFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMyCarrotBinding>(inflater, R.layout.fragment_my_carrot, container, false)
        binding.layoutSetting.setOnClickListener {
            startActivity(Intent(requireContext(), AppSettingActivity::class.java))
        }
        return binding.root
    }
}