package com.dongldh.carrot.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.dongldh.carrot.R
import com.dongldh.carrot.databinding.FragmentChatBinding

class ChatFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentChatBinding>(inflater, R.layout.fragment_chat, container, false)
        return binding.root
    }
}