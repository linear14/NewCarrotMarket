package com.dongldh.carrot.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.dongldh.carrot.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
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
        initListeners()

    }

    private fun initListeners() {
        bottom_navigation.setOnNavigationItemSelectedListener(this)
    }

}