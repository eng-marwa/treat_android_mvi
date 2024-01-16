package com.treat.customer.presentation.main

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import com.treat.customer.R
import com.treat.customer.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window
            .apply {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                statusBarColor = Color.TRANSPARENT
            }
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_home) as NavHostFragment
        navController = navHostFragment.navController


        navView.setupWithNavController(navController)
        navView.setOnItemSelectedListener { item ->
            var selectedTabPosition =0
            when(item.itemId){
                R.id.navigation_home -> {
                    selectedTabPosition = 0
                    navController.navigate(R.id.navigation_home)
                }
                R.id.navigation_booking -> {
                    selectedTabPosition = 1
                    navController.navigate(R.id.navigation_my_booking)

                }
                R.id.navigation_favorites -> {
                    selectedTabPosition = 2
                    navController.navigate(R.id.navigation_favorites)

                }
                R.id.navigation_more -> {
                    selectedTabPosition = 3
                    navController.navigate(R.id.navigation_more)

                }
            }
            val tabCount = navView.menu.size()
            val tabWidth = if (tabCount > 0) {
                navView.width / tabCount
            } else {
                0
            }
            val translationX =
                selectedTabPosition * tabWidth.toFloat() // Adjust tabWidth according to your layout
            // Create an ObjectAnimator to animate the translationX property
            val animator = ObjectAnimator.ofFloat(binding.navIcon, "translationX", translationX)
            animator.duration = 300 // Set the animation duration (adjust as needed)
            animator.start()
            true
        }
    }
}