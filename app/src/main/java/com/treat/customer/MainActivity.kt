package com.treat.customer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.treat.customer.databinding.ActivityItemBinding
import com.treat.customer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navGraph: NavGraph
    lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment)
        navController = navHostFragment.navController
        val inflater = navController.navInflater
        navGraph = inflater.inflate(R.navigation.auth_navigation)

        if(intent.hasExtra("Fragment")){
            navigateToLogin()
        }else{
            navigateToSplash()
        }
    }
    private fun navigateToLogin() {
        navGraph.setStartDestination(R.id.loginFragment)
        navController.graph = navGraph
    }

    private fun navigateToSplash() {
        navGraph.setStartDestination(R.id.splashFragment)
        navController.graph = navGraph
    }




}