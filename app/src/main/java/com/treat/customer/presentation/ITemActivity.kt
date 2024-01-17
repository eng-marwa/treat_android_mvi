package com.treat.customer.presentation

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call.Details
import android.util.Log
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.treat.customer.R
import com.treat.customer.data.model.SettingsData
import com.treat.customer.databinding.ActivityItemBinding

class ITemActivity : AppCompatActivity() {
    private var settingsApp: SettingsData? = null
    private lateinit var navGraph: NavGraph
    lateinit var navController: NavController
    private lateinit var binding: ActivityItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (intent.hasExtra("SettingsData")) {
            settingsApp = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra<SettingsData>("SettingsData", SettingsData::class.java)
            } else {
                intent.getParcelableExtra<SettingsData>("SettingsData")

            }
        }
        if (intent.hasExtra("ITEM")) {
            val extra = intent.getIntExtra("ITEM", 0)
            navigateTo(extra)
        }

    }

    private fun navigateTo(extra: Int) {
        val navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_item) as NavHostFragment)
        navController = navHostFragment.navController
        val inflater = navController.navInflater
        navGraph = inflater.inflate(R.navigation.item_navigation)

        when (extra) {
            R.string.my_profile -> {
                navGraph.setStartDestination(R.id.navigation_edit_profile)
                navController.graph = navGraph
            }

            R.string.my_points -> {
                navGraph.setStartDestination(R.id.navigation_my_points)
                navController.graph = navGraph
            }

            R.string.my_wallet -> {
                navGraph.setStartDestination(R.id.navigation_my_wallet)
                navController.graph = navGraph
            }

            R.string.terms -> {
                val bundle = Bundle().apply {
                    putString("terms", settingsApp?.terms)
                }
                navGraph.setStartDestination(R.id.navigation_terms_condition)
                navController.setGraph(navGraph, bundle)
            }

            R.string.contact_us -> {
                val bundle = Bundle().apply {
                    putString("whats", settingsApp?.whatsapp)
                    putString("email", settingsApp?.email)
                }
                navGraph.setStartDestination(R.id.navigation_contact_us)
                navController.setGraph(navGraph, bundle)
            }

            R.string.fqa -> {
                navGraph.setStartDestination(R.id.navigation_fqa)
                navController.graph = navGraph
            }

            R.string.change_language -> {
                navGraph.setStartDestination(R.id.navigation_change_language)
                navController.graph = navGraph
            }

            R.string.my_change_location -> {
//                navGraph.setStartDestination(R.id.navigation_fqa)
//                navController.graph = navGraph
            }

            R.string.branch_details -> {
                if (intent.hasExtra("BRANCH")) {
                    val branchId = intent.getStringExtra("BRANCH")
                    navGraph.setStartDestination(R.id.navigation_branch_details)
                    navController.setGraph(navGraph, bundleOf("branchId" to branchId))
                }
            }

            R.string.title_notifications -> {
                navGraph.setStartDestination(R.id.navigation_notification)
                navController.graph = navGraph
            }

            R.string.location_branch -> {
                navGraph.setStartDestination(R.id.navigation_branch_location)
                navController.graph = navGraph
            }

            else -> {}
        }
    }
}