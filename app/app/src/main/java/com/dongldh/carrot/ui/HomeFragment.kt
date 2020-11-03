package com.dongldh.carrot.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.dongldh.carrot.R
import com.dongldh.carrot.`interface`.OnDialogFragmentDismissListener
import com.dongldh.carrot.adapter.MainFragmentAdapter
import com.dongldh.carrot.databinding.FragmentHomeBinding
import com.dongldh.carrot.util.App
import com.dongldh.carrot.util.Util
import com.dongldh.carrot.viewmodel.SetMyRegionViewModel
import com.dongldh.carrot.viewmodel.UserViewModel
import com.dongldh.carrot.widget.RegionSelectorDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeFragment: Fragment() {
    val tabLayoutTextArray = arrayOf("중고거래", "동네생활")

    var isOpenRegionSelector = false

    val userViewModel: UserViewModel by sharedViewModel { parametersOf(App.pref.uid) }
    val setMyRegionViewModel: SetMyRegionViewModel by viewModel()

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater, R.layout.fragment_home, container, false)
        (activity as AppCompatActivity).setSupportActionBar(toolbar_home)

        binding.apply {
            layoutRegionSelectorHome.setOnClickListener {
                rotateArrow(imageUpDownArrow)
                showRegionSelectorWithDialogDismissListener()
            }

            setMyRegionViewModel.regionSelectedPair.observe(viewLifecycleOwner) { result ->
                toolbarTitleHome.text = result.second
            }

            viewPagerHome.adapter = MainFragmentAdapter(requireActivity().supportFragmentManager, lifecycle)
            TabLayoutMediator(layoutTab, viewPagerHome) { tab: TabLayout.Tab, position: Int ->
                tab.text = tabLayoutTextArray[position]
            }.attach()
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setMyRegionViewModel.initLiveData()
    }

    private fun rotateArrow(arrow: ImageView) {
        val arrowRotation: Animation

        if(!isOpenRegionSelector) {
            arrowRotation = AnimationUtils.loadAnimation(requireContext(), R.anim.animation_rotation_clockwise_half).apply{ fillAfter = true }
        } else {
            arrowRotation = AnimationUtils.loadAnimation(requireContext(), R.anim.animation_rotation_anticlockwise_half).apply{ fillAfter = true }
        }
        arrow.startAnimation(arrowRotation)
        isOpenRegionSelector = !isOpenRegionSelector
    }

    private fun showRegionSelectorWithDialogDismissListener() {
        activity?.supportFragmentManager?.let {
            RegionSelectorDialog().apply {
                setOnDismissListener(this)
                show(it, tag)
            }
        } ?: Util.showErrorToast()
    }

    private fun setOnDismissListener(dialog: RegionSelectorDialog) {
        dialog.setOnDialogFragmentDismissListener(object: OnDialogFragmentDismissListener {
            override fun onDismiss(region: Pair<Long, String>?) {
                rotateArrow(binding.imageUpDownArrow)
                region?.let {
                    setMyRegionViewModel.updateSelectedRegion(it)
                }
            }
        })
    }
}