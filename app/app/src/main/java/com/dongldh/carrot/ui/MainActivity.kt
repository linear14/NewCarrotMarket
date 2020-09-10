package com.dongldh.carrot.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.dongldh.carrot.R
import com.dongldh.carrot.util.App
import com.dongldh.carrot.viewmodel.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private val userViewModel: UserViewModel by viewModel { parametersOf(App.pref.uid) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val ft = supportFragmentManager.beginTransaction()
        when (item.itemId) {
            R.id.navigation_home -> ft.replace(R.id.fragment, HomeFragment()).commit()
            R.id.navigation_category -> ft.replace(R.id.fragment, CategoryFragment()).commit()
            R.id.navigation_write -> ft.replace(R.id.fragment, WriteFragment()).commit()
            R.id.navigation_chat -> ft.replace(R.id.fragment, ChatFragment()).commit()
            R.id.navigation_my_carrot -> ft.replace(R.id.fragment, MyCarrotFragment()).commit()
        }
        return true
    }

    private fun init() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment, HomeFragment()).commit()
        attachListeners()

    }

    private fun attachListeners() {
        bottom_navigation.setOnNavigationItemSelectedListener(this)
    }

}