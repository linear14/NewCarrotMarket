package com.dongldh.carrot.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.dongldh.carrot.R
import com.dongldh.carrot.databinding.FragmentCategoryBinding

class CategoryFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentCategoryBinding>(inflater, R.layout.fragment_category, container, false)
        return binding.root
    }
}