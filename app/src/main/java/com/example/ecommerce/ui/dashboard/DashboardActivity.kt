package com.example.ecommerce.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ecommerce.R
import com.example.ecommerce.databinding.ActivityDashboardBinding
import com.example.ecommerce.ui.dashboard.fragments.HomeFragment
import com.google.android.material.textfield.TextInputEditText

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private var isSearchVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDrawer()
        setupToolbar()
        loadFragment(HomeFragment())
    }

    private fun setupDrawer() {
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.topAppBar, 0, 0)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun setupToolbar() {
        binding.topAppBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_search -> {
                    toggleSearchBar()
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
        Log.d("DASHBOARD", "HomeFragment loaded successfully")
    }

    private fun toggleSearchBar() {
        if (isSearchVisible) {
            val existing = binding.topAppBar.findViewById<LinearLayout>(R.id.searchBarLayout)
            if (existing != null) binding.topAppBar.removeView(existing)
            isSearchVisible = false
        } else {
            val searchView = LayoutInflater.from(this)
                .inflate(R.layout.layout_search_bar, binding.topAppBar, false)
            val etSearch = searchView.findViewById<TextInputEditText>(R.id.etSearch)
            val closeIcon = searchView.findViewById<ImageView>(R.id.icClose)
            binding.topAppBar.addView(searchView)
            etSearch.requestFocus()
            closeIcon.setOnClickListener {
                binding.topAppBar.removeView(searchView)
                isSearchVisible = false
            }
            isSearchVisible = true
        }
    }
}
